package io.lozzikit.mail.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "mails")
public class MailEntity {
    private Integer id;
    private String templateName;

    private String from;
    private List<String> to;
    private List<String> cc;
    private List<String> cci;
    private Map<String, String> map;

    private String subject;
    private String effectiveContent;

    private JobEntity job;

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

    @Column(name = "mail_from")
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @ElementCollection
    @CollectionTable(name = "mail_to")
    @Column(name = "dest")
    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    @ElementCollection
    @CollectionTable(name = "mail_cc")
    @Column(name = "cc")
    public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    @ElementCollection
    @CollectionTable(name = "mail_cci")
    @Column(name = "cci")
    public List<String> getCci() {
        return cci;
    }

    public void setCci(List<String> cci) {
        this.cci = cci;
    }

    @ElementCollection
    @CollectionTable(name = "mail_map")
    @Column(name = "map")
    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    @Column(name = "subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Column(name = "effective")
    public String getEffectiveContent() {
        return effectiveContent;
    }

    public void setEffectiveContent(String effectiveContent) {
        this.effectiveContent = effectiveContent;
    }

    @OneToOne(mappedBy = "mail")
    public JobEntity getJob() {
        return job;
    }

    public void setJob(JobEntity job) {
        this.job = job;
    }
}
