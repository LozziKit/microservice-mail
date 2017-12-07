package io.lozzikit.mail.util;

import io.lozzikit.mail.api.model.ArchivedMailDto;
import io.lozzikit.mail.api.model.JobDto;
import io.lozzikit.mail.api.model.TemplateDto;
import io.lozzikit.mail.entity.JobEntity;
import io.lozzikit.mail.entity.MailEntity;
import io.lozzikit.mail.entity.TemplateEntity;
import org.modelmapper.ModelMapper;

public class DtoFactory {
    private static ModelMapper modelMapper = new ModelMapper();

    public static JobDto createFrom(JobEntity entity) {
        return modelMapper.map(entity, JobDto.class);
    }

    public static ArchivedMailDto createFrom(MailEntity entity) {
        return modelMapper.map(entity, ArchivedMailDto.class);
    }

    public static TemplateDto createFrom(TemplateEntity entity) {
        return modelMapper.map(entity, TemplateDto.class);
    }
}
