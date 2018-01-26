package io.lozzikit.mail.entity;

import io.lozzikit.mail.model.StatusEnum;

import javax.persistence.*;

/**
 * Job POJO
 */
@Entity
@Table(name = "jobs")
public class JobEntity {
    private Integer id;
    private StatusEnum status;

    private MailEntity mail;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToOne
    public MailEntity getMail() {
        return mail;
    }

    public void setMail(MailEntity mail) {
        this.mail = mail;
    }

    @Column(name = "status")
    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
