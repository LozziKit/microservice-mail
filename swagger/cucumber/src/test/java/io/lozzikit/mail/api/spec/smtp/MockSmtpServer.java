package io.lozzikit.mail.api.spec.smtp;

import org.subethamail.wiser.Wiser;

public class MockSmtpServer {
    private Wiser wiser;

    public MockSmtpServer() {
        wiser = new Wiser();
        wiser.start();
    }
}
