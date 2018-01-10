package io.lozzikit.mail.api.spec;

import cucumber.api.cli.Main;
import io.lozzikit.mail.api.spec.smtp.MockSmtpServer;

public class CliRunner {
    public static void main(String[] args) throws Throwable {
        String[] arguments = {"--glue", "io.lozzikit.mail.api.spec.steps", "classpath:scenarios"};

        Main.main(arguments);
    }
}
