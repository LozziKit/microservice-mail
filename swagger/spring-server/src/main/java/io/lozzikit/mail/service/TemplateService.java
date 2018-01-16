package io.lozzikit.mail.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import io.lozzikit.mail.api.model.TemplateDto;
import io.lozzikit.mail.entity.TemplateEntity;
import io.lozzikit.mail.repository.TemplateRepository;
import io.lozzikit.mail.util.DtoFactory;
import io.lozzikit.mail.util.EntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemplateService {
    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private Configuration configuration;

    public TemplateDto addTemplate(TemplateDto templateDto) throws IOException {
        TemplateEntity entity = EntityFactory.createFrom(templateDto);
        validateTemplate(templateDto);

        templateRepository.save(entity);
        return DtoFactory.createFrom(entity);
    }

    public List<TemplateDto> getAllTemplates() {
        return templateRepository.findAll().stream()
            .map(DtoFactory::createFrom)
            .collect(Collectors.toList());
    }

    public TemplateDto getTemplateById(Integer id) {
        return DtoFactory.createFrom(templateRepository.findOne(id));
    }

    public TemplateDto getTemplateByName(String name) {
        TemplateEntity entity = templateRepository.findOneByName(name);
        return DtoFactory.createFrom(entity);
    }

    public void updateTemplate(String name, TemplateDto templateDto) throws IOException {
        validateTemplate(templateDto);

        TemplateEntity entity = templateRepository.findOneByName(name);
        entity.setDescription(templateDto.getDescription());
        entity.setContent(templateDto.getContent());

        templateRepository.save(entity);
    }

    public long deleteTemplate(String name) {
        return templateRepository.deleteOneByName(name);
    }

    private void validateTemplate(TemplateDto templateDto) throws IOException {
        String content = templateDto.getContent();

        if(content == null) {
            throw new IOException("Template is null!");
        }

        String[] lines = content.split("\n\n");
        if(lines.length < 2) {
            throw new IOException("Template does not contain a subject header");
        }

        new Template("sample", new StringReader(content), configuration);
    }
}
