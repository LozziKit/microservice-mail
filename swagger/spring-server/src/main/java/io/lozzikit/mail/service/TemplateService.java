package io.lozzikit.mail.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import io.lozzikit.mail.api.model.TemplateDto;
import io.lozzikit.mail.entity.TemplateEntity;
import io.lozzikit.mail.repository.TemplateRepository;
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
        TemplateEntity entity = new TemplateEntity(templateDto);
        validateTemplate(templateDto);

        templateRepository.save(entity);
        return entity.getTemplateDto();
    }

    public List<TemplateDto> getAllTemplates() {
        return templateRepository.findAll().stream()
            .map(TemplateEntity::getTemplateDto)
            .collect(Collectors.toList());
    }

    public TemplateDto getTemplateById(Integer id) {
        return templateRepository.findOne(id).getTemplateDto();
    }

    public TemplateDto getTemplateByName(String name) {
        return templateRepository.findOneByName(name).getTemplateDto();
    }

    public void updateTemplate(String name, TemplateDto templateDto) throws IOException {
        validateTemplate(templateDto);

        TemplateEntity entity = templateRepository.findOneByName(name);
        entity.setDescription(templateDto.getDescription());
        entity.setContent(templateDto.getContent());

        templateRepository.save(entity);
    }

    public void deleteTemplate(String name) {
        TemplateEntity entity = templateRepository.findOneByName(name);
        templateRepository.delete(entity);
    }

    private void validateTemplate(TemplateDto templateDto) throws IOException {
        String content = templateDto.getContent();

        if (content == null) {
            throw new IOException("Template is null!");
        }

        new Template("sample", new StringReader(content), configuration);
    }
}
