package io.lozzikit.mail.entity;

import io.lozzikit.mail.api.model.TemplateDto;

import javax.persistence.*;

@Entity
@Table(name = "templates")
public class TemplateEntity {
    private Integer id;
    private String name;
    private String description;
    private String content;

    public TemplateEntity() {
    }

    public TemplateEntity(TemplateDto templateDto) {
        this.name = templateDto.getName();
        this.description = templateDto.getDescription();
        this.content = templateDto.getContent();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Transient
    public TemplateDto getTemplateDto() {
        TemplateDto templateDto = new TemplateDto();
        templateDto.setId(id);
        templateDto.setName(name);
        templateDto.setDescription(description);
        templateDto.setContent(content);

        return templateDto;
    }
}
