package io.lozzikit.mail.smtp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmtpMailer {
    @Autowired
    private SmtpServerDescription serverDescription;
}
