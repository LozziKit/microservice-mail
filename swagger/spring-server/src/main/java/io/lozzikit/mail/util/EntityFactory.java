package io.lozzikit.mail.util;

import io.lozzikit.mail.api.model.JobDto;
import io.lozzikit.mail.api.model.MailDto;
import io.lozzikit.mail.api.model.TemplateDto;
import io.lozzikit.mail.entity.JobEntity;
import io.lozzikit.mail.entity.MailEntity;
import io.lozzikit.mail.entity.TemplateEntity;
import org.modelmapper.ModelMapper;

public class EntityFactory {
    private static ModelMapper modelMapper = configureModelMapper();

    private static ModelMapper configureModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper;
    }

    public static JobEntity createFrom(JobDto dto) {
        return modelMapper.map(dto, JobEntity.class);
    }

    public static MailEntity createFrom(MailDto dto) {
        return modelMapper.map(dto, MailEntity.class);
    }

    public static TemplateEntity createFrom(TemplateDto dto) {
        return modelMapper.map(dto, TemplateEntity.class);
    }
}
