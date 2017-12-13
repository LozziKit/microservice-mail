package io.lozzikit.mail.service;

import io.lozzikit.mail.api.model.ArchivedMailDto;
import io.lozzikit.mail.api.model.JobDto;
import io.lozzikit.mail.api.model.MailDto;
import io.lozzikit.mail.entity.JobEntity;
import io.lozzikit.mail.model.StatusEnum;
import io.lozzikit.mail.repository.JobRepository;
import io.lozzikit.mail.repository.MailRepository;
import io.lozzikit.mail.smtp.SmtpMailer;
import io.lozzikit.mail.util.DtoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

@Service
public class MailService {
    private static final Integer MAX_MAIL_TO_SEND = 25;
    private static final Integer TIME_TO_SLEEP = 20 * 1000;

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SmtpMailer mailer;

    private BlockingQueue<Pair<MailDto, Integer>> mailingQueue;

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

                jobDtos.add(DtoFactory.createFrom(jobEntity));
                mailingQueue.put(Pair.of(mailDto, jobEntity.getId()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return jobDtos;
    }

    public List<ArchivedMailDto> getAllMails() {
        return mailRepository.findAll().stream()
            .map(DtoFactory::createFrom)
            .collect(Collectors.toList());
    }

    public ArchivedMailDto getMailById(Integer id) {
        return DtoFactory.createFrom(mailRepository.findOne(id));
    }

    public class MailingTask implements Runnable {
        @Override
        public void run() {
            List<Pair<MailDto, Integer>> pairs = new ArrayList<>();

            while (true) {
                try {
                    pairs.add(mailingQueue.take());
                    mailingQueue.drainTo(pairs, MAX_MAIL_TO_SEND - 1);

                    for (Pair<MailDto, Integer> pair : pairs) {
                        JobEntity jobEntity = jobRepository.findOne(pair.getSecond());

                        if(jobEntity.getStatus() != StatusEnum.CANCELLED) {
                            Boolean success = mailer.sendMail(pair.getFirst(), "");

                            if(success) {
                                jobEntity.setStatus(StatusEnum.DONE);
                            } else {
                                jobEntity.setStatus(StatusEnum.FAILED);
                            }

                            jobRepository.save(jobEntity);
                        }
                    }

                    pairs.clear();
                    Thread.sleep(TIME_TO_SLEEP);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
