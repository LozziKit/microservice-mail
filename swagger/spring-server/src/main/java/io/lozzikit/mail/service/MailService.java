package io.lozzikit.mail.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.lozzikit.mail.api.model.ArchivedMailDto;
import io.lozzikit.mail.api.model.JobDto;
import io.lozzikit.mail.api.model.MailDto;
import io.lozzikit.mail.entity.JobEntity;
import io.lozzikit.mail.entity.MailEntity;
import io.lozzikit.mail.entity.TemplateEntity;
import io.lozzikit.mail.model.StatusEnum;
import io.lozzikit.mail.repository.JobRepository;
import io.lozzikit.mail.repository.MailRepository;
import io.lozzikit.mail.repository.TemplateRepository;
import io.lozzikit.mail.smtp.SmtpMailer;
import io.lozzikit.mail.util.DtoFactory;
import io.lozzikit.mail.util.EntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

@Service
public class MailService {
    @Value("${io.lozzikit.smtp.max.mail.throughput}")
    private Integer MAX_MAIL_TO_SEND;

    @Value("${io.lozzikit.smtp.milliseconds.before.sending}")
    private Integer TIME_TO_SLEEP;

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private Configuration configuration;

    @Autowired
    private SmtpMailer mailer;

    private BlockingQueue<Pair<MailEntity, Integer>> mailingQueue;

    public MailService() {
        mailingQueue = new LinkedBlockingQueue<>();

        Thread mailingThread = new Thread(new MailingTask());
        mailingThread.start();
    }

    public List<JobDto> sendMails(List<MailDto> mailDtos) {
        List<JobDto> jobDtos = new ArrayList<>();

        for (MailDto mailDto : mailDtos) {
            try {
                JobEntity jobEntity = new JobEntity();
                jobEntity.setStatus(StatusEnum.ONGOING);
                jobEntity = jobRepository.save(jobEntity);

                MailEntity mailEntity = EntityFactory.createFrom(mailDto);
                mailEntity = mailRepository.save(mailEntity);
                mailEntity.setJob(jobEntity);
                jobEntity.setMail(mailEntity);

                // Render template
                TemplateEntity template = templateRepository.findOneByName(mailEntity.getTemplateName());
                if(template != null) {
                    try {
                        Template temp = new Template("sample", new StringReader(template.getContent()), configuration);
                        StringWriter writer = new StringWriter();
                        temp.process(mailEntity.getMap(), writer);

                        String[] parts = writer.toString().split("\n\n");
                        for(String line : parts[0].split("\n")) {
                            String[] lineParts = line.split(":");

                            String property = lineParts[0];
                            String value = lineParts[1];

                            if(property.toLowerCase().equals("subject")) {
                                mailEntity.setSubject(value);
                            }
                        }

                        String effectiveContent = String.join("\n\n", Arrays.copyOfRange(parts, 1, parts.length));
                        mailEntity.setEffectiveContent(effectiveContent);

                        mailingQueue.put(Pair.of(mailEntity, jobEntity.getId()));
                    } catch (IOException | TemplateException e) {
                        // Template error or
                        jobEntity.setStatus(StatusEnum.INVALID);
                        jobEntity = jobRepository.save(jobEntity);
                    }
                } else {
                    // Template not found.
                    jobEntity.setStatus(StatusEnum.INVALID);
                    jobEntity = jobRepository.save(jobEntity);
                }

                mailRepository.save(mailEntity);
                jobDtos.add(DtoFactory.createFrom(jobEntity));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return jobDtos;
    }

    public List<ArchivedMailDto> getAllMails() {
        return mailRepository.findAllByOrderByIdDesc().stream()
            .map(DtoFactory::createFrom)
            .collect(Collectors.toList());
    }

    public ArchivedMailDto getMailById(Integer id) {
        return DtoFactory.createFrom(mailRepository.findOne(id));
    }

    public class MailingTask implements Runnable {
        @Override
        public void run() {
            List<Pair<MailEntity, Integer>> pairs = new ArrayList<>();

            while (true) {
                try {
                    pairs.add(mailingQueue.take());
                    mailingQueue.drainTo(pairs, MAX_MAIL_TO_SEND - 1);
                    Thread.sleep(TIME_TO_SLEEP);

                    for (Pair<MailEntity, Integer> pair : pairs) {
                        JobEntity jobEntity = jobRepository.findOne(pair.getSecond());

                        if(jobEntity.getStatus() != StatusEnum.CANCELLED) {
                            Boolean success = mailer.sendMail(pair.getFirst());

                            if(success) {
                                jobEntity.setStatus(StatusEnum.DONE);
                            } else {
                                jobEntity.setStatus(StatusEnum.FAILED);
                            }

                            jobRepository.save(jobEntity);
                        }
                    }

                    pairs.clear();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
