package io.lozzikit.mail.entity;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

/**
 * Template POJO
 */
@Entity
@Table(name = "templates")
public class TemplateEntity {
    private Integer id;
    private String name;
    private String description;
    private String content;

    @Value("${server.contextPath}")
    private String contextPath;

    public TemplateEntity() {
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

    @Column(name = "name", unique = true)
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
}
