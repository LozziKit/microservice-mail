package io.lozzikit.mail.entity;

import io.lozzikit.mail.api.model.ArchivedMailDto;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Entity
@Table(name = "mails")
public class MailEntity {

    @Value("${server.contextPath}")
    private String contextPath;

    private Integer id;
    private String templateName;
    private PayloadEntity payload;
    private String effectiveContent;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "template_name")
    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    @OneToOne
    public PayloadEntity getPayload() {
        return payload;
    }

    public void setPayload(PayloadEntity payload) {
        this.payload = payload;
    }

    @Column(name = "effective")
    public String getEffectiveContent() {
        return effectiveContent;
    }

    public void setEffectiveContent(String effectiveContent) {
        this.effectiveContent = effectiveContent;
    }

    @Transient
    public ArchivedMailDto getArchivedMailDto() {
        ArchivedMailDto archivedMailDto = new ArchivedMailDto();
        archivedMailDto.setUrl(contextPath + "/mails/" + id);
        archivedMailDto.setTemplateName(templateName);
        archivedMailDto.setPayload(payload.getPayloadDto());
        archivedMailDto.setEffectiveContent(effectiveContent);

        return archivedMailDto;
    }
}
