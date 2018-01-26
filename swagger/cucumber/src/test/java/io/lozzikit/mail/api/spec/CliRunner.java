package io.lozzikit.mail.api.spec;

import cucumber.api.cli.Main;

public class CliRunner {
    public static void main(String[] args) throws Throwable {
        /**
         * Small hack to avoid compiling test in docker container,
         * we compile them before creating the docker image and then
         * link the test to the docker container
         */
        String[] arguments = {"--glue", "io.lozzikit.mail.api.spec.steps", "classpath:scenarios"};

        Main.main(arguments);
    }
}
