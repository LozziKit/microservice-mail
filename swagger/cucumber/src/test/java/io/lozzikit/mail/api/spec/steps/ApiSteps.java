package io.lozzikit.mail.api.spec.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import io.lozzikit.mail.api.spec.helpers.Environment;

import static org.junit.Assert.assertEquals;

/**
 * Steps for Cucumber routine testing
 */
public class ApiSteps {
    // Link to Environment
    private Environment env;

    /**
     * Ctor
     *
     * @param environment
     */
    public ApiSteps(Environment environment) {
        this.env = environment;
    }

    /**
     * Testing for a given status code
     *
     * @param status status code to assert
     */
    @Then("^I receive a (\\d+) status code$")
    public void I_Receive_A_Status_code(int status) {
        assertEquals(status, env.getApiStatusCode());
    }

    /**
     * Sleep interruption for timed testing
     *
     * @param milliseconds time to sleep milliseconds
     * @throws Throwable if thread dies
     */
    @And("^I wait (\\d+) milliseconds$")
    public void I_Wait_Milliseconds(int milliseconds) throws Throwable {
        Thread.sleep(milliseconds);
    }
}
