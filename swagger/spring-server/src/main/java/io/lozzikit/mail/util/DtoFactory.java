package io.lozzikit.mail.util;

import io.lozzikit.mail.api.model.ArchivedMailDto;
import io.lozzikit.mail.api.model.JobDto;
import io.lozzikit.mail.api.model.TemplateDto;
import io.lozzikit.mail.entity.JobEntity;
import io.lozzikit.mail.entity.MailEntity;
import io.lozzikit.mail.entity.TemplateEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DtoFactory {
    private static String contextPath;
    private static ModelMapper modelMapper = configureModelMapper();

    @Value("${server.contextPath}")
    public void setContextPath(String contextPath) {
        DtoFactory.contextPath = contextPath;
    }

    private static Converter<Integer, String> getUrlConverter(String resource) {
        return ctx -> contextPath + resource + "/" + ctx.getSource();
    }

    private static ModelMapper configureModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(TemplateEntity.class, TemplateDto.class)
            .addMappings(mapper -> mapper
                .using(getUrlConverter("/templates"))
                .map(TemplateEntity::getName, TemplateDto::setUrl));

        modelMapper.typeMap(JobEntity.class, JobDto.class)
            .addMappings(mapper -> {
                mapper.using(getUrlConverter("/jobs"))
                    .map(JobEntity::getId, JobDto::setUrl);

                mapper.using(getUrlConverter("/mails"))
                    .map(j -> j.getMail().getId(), JobDto::setMail);
            });

        modelMapper.typeMap(MailEntity.class, ArchivedMailDto.class)
            .addMappings(mapper -> mapper
                .using(getUrlConverter("/mails"))
                .map(MailEntity::getId, ArchivedMailDto::setUrl));

        return modelMapper;
    }

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
