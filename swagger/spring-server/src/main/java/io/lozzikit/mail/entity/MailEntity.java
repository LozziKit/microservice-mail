package io.lozzikit.mail.entity;

import io.lozzikit.mail.api.model.ArchivedMailDto;

import javax.persistence.*;

@Entity
@Table(name = "mails")
public class MailEntity {
    private Integer id;
    private Integer templateId;
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

    @Column(name = "template_id")
    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
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
        archivedMailDto.setId(id);
        archivedMailDto.setTemplateId(templateId);
        archivedMailDto.setPayload(payload.getPayloadDto());
        archivedMailDto.setEffectiveContent(effectiveContent);

        return archivedMailDto;
    }
}
