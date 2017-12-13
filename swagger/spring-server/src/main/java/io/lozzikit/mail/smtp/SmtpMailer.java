package io.lozzikit.mail.smtp;

import io.lozzikit.mail.api.model.MailDto;
import org.simplejavamail.MailException;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.ServerConfig;
import org.slf4j.LoggerFactory;
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

    public Boolean sendMail(MailDto mailDto, String body) {
        EmailBuilder builder = new EmailBuilder()
            .from(mailDto.getFrom(), mailDto.getFrom())
            .to(join(mailDto.getTo()))
            .text(body);

        if(mailDto.getCc().size() != 0) {
            builder.cc(join(mailDto.getCc()));
        }

        if(mailDto.getCci().size() != 0) {
            builder.bcc(join(mailDto.getCci()));
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
