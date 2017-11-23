package io.lozzikit.mail.api.spec.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.lozzikit.mail.ApiException;
import io.lozzikit.mail.api.TemplateApi;
import io.lozzikit.mail.api.dto.TemplateDto;
import io.lozzikit.mail.api.spec.helpers.Environment;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Olivier Liechti on 27/07/17.
 */
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
