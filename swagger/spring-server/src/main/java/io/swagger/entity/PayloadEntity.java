package io.swagger.entity;

import io.lozzikit.mail.api.model.PayloadDto;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "payloads")
public class PayloadEntity {
    private Integer id;
    private MailEntity mail;
    private String from;
    private List<String> to;
    private List<String> cc;
    private List<String> cci;
    private Map<String, String> map;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToOne(mappedBy = "payload")
    @JoinColumn(name = "mail_id")
    public MailEntity getMail() {
        return mail;
    }

    public void setMail(MailEntity mail) {
        this.mail = mail;
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
    @Column(name = "to")
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

    @Transient
    public PayloadDto getPayloadDto() {
        PayloadDto payloadDto = new PayloadDto();
        payloadDto.setFrom(from);
        payloadDto.setTo(to);
        payloadDto.setCc(cc);
        payloadDto.setCci(cci);
        payloadDto.setMap(map);

        return payloadDto;
    }
}
