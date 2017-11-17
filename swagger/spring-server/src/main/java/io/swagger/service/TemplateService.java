package io.swagger.service;

import io.lozzikit.mail.api.model.TemplateDto;
import io.swagger.entity.TemplateEntity;
import io.swagger.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemplateService {
    @Autowired
    private TemplateRepository templateRepository;

    public void addTemplate(TemplateDto templateDto) {
        TemplateEntity entity = new TemplateEntity(templateDto);
        templateRepository.save(entity);
    }

    public List<TemplateDto> getAllTemplates() {
        return templateRepository.findAll().stream()
            .map(TemplateEntity::getTemplateDto)
            .collect(Collectors.toList());
    }
}
