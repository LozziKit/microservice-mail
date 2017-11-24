package io.lozzikit.mail.smtp;

import org.springframework.stereotype.Component;

@Component
public interface SmtpServerDescription {
    String getAddress();

    Integer getPort();
}
