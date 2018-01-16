package io.lozzikit.mail.smtp;

import io.lozzikit.mail.entity.MailEntity;
import org.simplejavamail.MailException;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.ServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class SmtpMailer {
    @Value("${io.lozzikit.smtp.debug}")
    private Boolean debug;

    @Value("${io.lozzikit.smtp.address}")
    private String address;

    @Value("${io.lozzikit.smtp.port}")
    private Integer port;

    private Mailer mailer;

    @PostConstruct
    public void init() {
        mailer = new Mailer(new ServerConfig(address, port));
        mailer.setDebug(debug);
    }

    public Boolean sendMail(MailEntity mail) {
        EmailBuilder builder = new EmailBuilder()
            .from(mail.getFrom(), mail.getFrom())
            .to(join(mail.getTo()))
            .subject(mail.getSubject())
            .text(mail.getEffectiveContent());

        if(mail.getCc().size() != 0) {
            builder.cc(join(mail.getCc()));
        }

        if(mail.getCci().size() != 0) {
            builder.bcc(join(mail.getCci()));
        }

        try {
            mailer.sendMail(builder.build());
            return true;
        } catch (MailException e) {
            e.printStackTrace();
        }

        return false;
    }

    private String join(List<String> strings) {
        return String.join(";", strings);
    }
}
