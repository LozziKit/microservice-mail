package io.lozzikit.mail.api.spec.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import io.lozzikit.mail.api.spec.helpers.Environment;

import static org.junit.Assert.assertEquals;

public class ApiSteps {
    private Environment env;

    public ApiSteps(Environment environment) {
        this.env = environment;
    }

    @Then("^I receive a (\\d+) status code$")
    public void I_Receive_A_Status_code(int status) throws Throwable {
        assertEquals(status, env.getApiStatusCode());
    }

    @And("^I wait (\\d+) milliseconds$")
    public void I_Wait_Milliseconds(int milliseconds) throws Throwable {
        Thread.sleep(milliseconds);
    }
}
