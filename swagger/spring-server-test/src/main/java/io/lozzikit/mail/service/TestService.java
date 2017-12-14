package io.lozzikit.mail.service;

import io.lozzikit.mail.entity.JobEntity;
import io.lozzikit.mail.entity.MailEntity;
import io.lozzikit.mail.entity.TemplateEntity;
import io.lozzikit.mail.model.StatusEnum;
import io.lozzikit.mail.repository.JobRepository;
import io.lozzikit.mail.repository.MailRepository;
import io.lozzikit.mail.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TestService {
    @Autowired
    private TemplateRepository templateRepository;
    @Autowired
    private MailRepository mailRepository;
    @Autowired
    private JobRepository jobRepository;

    public void deleteTemplates() {
        templateRepository.deleteAll();
    }

    public void deleteJobs() {
        jobRepository.deleteAll();
    }

    public void deleteMails() {
        mailRepository.deleteAll();
    }

    public boolean populateTemplates(String set) {
        switch (set.toLowerCase()) {
            case "default":
                TemplateEntity template1 = new TemplateEntity();
                template1.setName("test-template-1");
                template1.setDescription("A first template for testing");
                template1.setContent("Hello ${name}!");
                templateRepository.save(template1);

                TemplateEntity template2 = new TemplateEntity();
                template2.setName("test-template-2");
                template2.setDescription("A second template for testing");
                template1.setContent("Goodbye ${name}!");
                templateRepository.save(template2);
                break;
            default:
                return false;
        }
        return true;
    }

    public boolean populateJobsAndMails(String set) {
        switch (set.toLowerCase()) {
            case "default":
                final String from = "z.z@z.org";

                Map<String, String> map;
                List<String> dests;

                MailEntity mail1 = new MailEntity();
                mail1.setTemplateName("test-template-1");
                mail1.setFrom(from);

                dests = new ArrayList<>(Collections.singletonList("y.y@y.org"));
                mail1.setTo(dests);

                map = new TreeMap<>();
                map.put("name", "y");
                mail1.setMap(map);
                mailRepository.save(mail1);

                MailEntity mail2 = new MailEntity();
                mail2.setTemplateName("test-template-2");
                mail2.setFrom(from);

                dests = new ArrayList<>(Collections.singletonList("x.x@x.org"));
                mail2.setTo(dests);

                map = new TreeMap<>();
                map.put("name", "x");
                mail2.setMap(map);
                mailRepository.save(mail2);

                MailEntity mail3 = new MailEntity();
                mail3.setTemplateName("test-template-2");
                mail3.setFrom(from);

                dests = new ArrayList<>(Collections.singletonList("v.v@v.org"));
                mail3.setTo(dests);

                map = new TreeMap<>();
                map.put("name", "v");
                mail3.setMap(map);
                mailRepository.save(mail3);

                MailEntity mail4 = new MailEntity();
                mail4.setTemplateName("test-template-2");
                mail4.setFrom(from);

                dests = new ArrayList<>(Collections.singletonList("w.w@w.org"));
                mail4.setTo(dests);

                map = new TreeMap<>();
                map.put("name", "w");
                mail4.setMap(map);
                mailRepository.save(mail4);

                JobEntity jobDone = new JobEntity();
                jobDone.setStatus(StatusEnum.DONE);
                jobDone.setMail(mail1);
                jobRepository.save(jobDone);

                JobEntity jobFailed = new JobEntity();
                jobFailed.setStatus(StatusEnum.FAILED);
                jobFailed.setMail(mail2);
                jobRepository.save(jobFailed);

                JobEntity jobInvalid = new JobEntity();
                jobInvalid.setStatus(StatusEnum.INVALID);
                jobInvalid.setMail(mail3);
                jobRepository.save(jobInvalid);

                JobEntity jobOngoing = new JobEntity();
                jobOngoing.setStatus(StatusEnum.ONGOING);
                jobOngoing.setMail(mail4);
                jobRepository.save(jobOngoing);

                break;
            default:
                return false;
        }
        return true;
    }

    public Integer getOneMailId() {
        List<MailEntity> mails = mailRepository.findAll();
        return mails.get(0).getId();
    }

    public Integer getOneJobId(String status) {
        List<JobEntity> jobs = jobRepository.findAll();
        if(status != null) {
            jobs = jobs
                    .stream()
                    .filter(j -> j.getStatus().toString().equals(status.toUpperCase()))
                    .collect(Collectors.toList());
        }
        return jobs.get(0).getId();
    }
}
