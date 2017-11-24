package io.lozzikit.mail.api.spec.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.lozzikit.mail.api.spec.helpers.Environment;

import static org.junit.Assert.*;

public class ApiSteps {
    private Environment env;

    public ApiSteps(Environment environment) {
        this.env = environment;
    }

    @Given("^An empty database$")
    public void an_empty_database() throws Throwable {
    }

    @Then("^I receive a (\\d+) status code$")
    public void i_receive_a_status_code(int status) throws Throwable {
        assertEquals(status, env.getApiStatusCode());
    }
}
