package io.lozzikit.mail.service;

import io.lozzikit.mail.api.model.ArchivedMailDto;
import io.lozzikit.mail.api.model.JobDto;
import io.lozzikit.mail.api.model.MailDto;
import io.lozzikit.mail.repository.MailRepository;
import io.lozzikit.mail.smtp.SmtpMailer;
import io.lozzikit.mail.util.DtoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MailService {
    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private SmtpMailer mailer;

    public List<JobDto> sendMail(List<MailDto> mails) {
        return null;
    }

    public List<ArchivedMailDto> getAllMails() {
        return mailRepository.findAll().stream()
            .map(DtoFactory::createFrom)
            .collect(Collectors.toList());
    }

    public ArchivedMailDto getMailById(Integer id) {
        return DtoFactory.createFrom(mailRepository.findOne(id));
    }
}
