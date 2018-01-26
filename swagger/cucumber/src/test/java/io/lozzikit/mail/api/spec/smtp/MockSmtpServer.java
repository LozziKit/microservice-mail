package io.lozzikit.mail.api.spec.smtp;


import com.dumbster.smtp.SimpleSmtpServer;

import java.io.IOException;

/**
 * Mock SMTP Server, used to test the mail service, uses Wiser for mocking
 */
public class MockSmtpServer {
    private SimpleSmtpServer server;

    /**
     * Ctor
     */
    public MockSmtpServer() {
        try {
            server = SimpleSmtpServer.start(SimpleSmtpServer.DEFAULT_SMTP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter for mock Server
     *
     * @return SimpleSmtpServer
     */
    public SimpleSmtpServer getWiser() {
        return server;
    }
}
