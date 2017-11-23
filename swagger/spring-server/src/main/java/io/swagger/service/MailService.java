package io.swagger.service;

import io.lozzikit.mail.api.model.ArchivedMailDto;
import io.lozzikit.mail.api.model.JobDto;
import io.lozzikit.mail.api.model.MailDto;
import io.swagger.entity.MailEntity;
import io.swagger.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MailService {
    @Autowired
    private MailRepository mailRepository;

    public List<JobDto> sendMail(List<MailDto> mails) {
        return null;
    }

    public List<ArchivedMailDto> getAllMails() {
        return mailRepository.findAll().stream()
            .map(MailEntity::getArchivedMailDto)
            .collect(Collectors.toList());
    }

    public ArchivedMailDto getMailById(Integer id) {
        return mailRepository.findOne(id).getArchivedMailDto();
    }
}
