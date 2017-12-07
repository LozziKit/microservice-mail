package io.lozzikit.mail.entity;

import io.lozzikit.mail.model.StatusEnum;

import javax.persistence.*;

@Entity
@Table(name = "jobs")
public class JobEntity {
    private Integer id;
    private String url;
    private StatusEnum status;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "status")
    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
