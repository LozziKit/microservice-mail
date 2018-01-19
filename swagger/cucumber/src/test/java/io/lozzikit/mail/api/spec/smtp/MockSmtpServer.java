package io.lozzikit.mail.api.spec.smtp;


import com.dumbster.smtp.SimpleSmtpServer;

import java.io.IOException;

public class MockSmtpServer {
    private SimpleSmtpServer server;

    public MockSmtpServer() {
        try {
            server = SimpleSmtpServer.start(SimpleSmtpServer.DEFAULT_SMTP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SimpleSmtpServer getWiser() {
        return server;
    }
}
