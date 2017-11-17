package io.swagger.entity;

import io.lozzikit.mail.api.model.TemplateDto;

import javax.persistence.*;

@Entity
@Table(name = "templates")
public class TemplateEntity {
    private TemplateDto templateDto;

    public TemplateEntity() {
        templateDto = new TemplateDto();
    }

    public TemplateEntity(TemplateDto templateDto) {
        this.templateDto = templateDto;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        return templateDto.getId();
    }

    public void setId(Integer id) {
        templateDto.setId(id);
    }

    @Column(name = "name")
    public String getName() {
        return templateDto.getName();
    }

    public void setName(String name) {
        templateDto.setName(name);
    }

    @Column(name = "description")
    public String getDescription() {
        return templateDto.getDescription();
    }

    public void setDescription(String description) {
        templateDto.setDescription(description);
    }

    @Column(name = "content")
    public String getContent() {
        return templateDto.getContent();
    }

    public void setContent(String content) {
        templateDto.setContent(content);
    }

    @Transient
    public TemplateDto getTemplateDto() {
        return templateDto;
    }
}
