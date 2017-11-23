package io.swagger.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import io.lozzikit.mail.api.model.TemplateDto;
import io.swagger.entity.TemplateEntity;
import io.swagger.repository.TemplateRepository;
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

    public void updateTemplate(Integer id, TemplateDto templateDto) throws IOException {
        validateTemplate(templateDto);

        TemplateEntity entity = templateRepository.findOne(id);
        entity.setName(templateDto.getName());
        entity.setDescription(templateDto.getDescription());
        entity.setContent(templateDto.getContent());

        templateRepository.save(entity);
    }

    public void deleteTemplate(Integer id) {
        templateRepository.delete(id);
    }

    private void validateTemplate(TemplateDto templateDto) throws IOException {
        String content = templateDto.getContent();

        if (content == null) {
            throw new IOException("Template is null!");
        }

        new Template("sample", new StringReader(content), configuration);
    }
}
