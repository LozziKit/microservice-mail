package io.lozzikit.mail.api.spec.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.lozzikit.mail.ApiException;
import io.lozzikit.mail.ApiResponse;
import io.lozzikit.mail.api.TemplateApi;
import io.lozzikit.mail.api.dto.TemplateDto;
import io.lozzikit.mail.api.spec.helpers.Environment;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by Olivier Liechti on 27/07/17.
 */
public class CreationSteps {

    private Environment environment;
    private TemplateApi api;

    TemplateDto template;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    public CreationSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("^there is a templates endpoint$")
    public void there_is_a_templates_endpoint() throws Throwable {
        assertNotNull(api);
    }

    @Given("^I have a template payload$")
    public void i_have_a_template_payload() throws Throwable {
        template = new TemplateDto();
    }

    @Then("^I receive a (\\d+) status code$")
    public void i_receive_a_status_code(int arg1) throws Throwable {
        assertEquals(arg1, lastStatusCode);
    }

    @Then("^I receive an id of the template$")
    public void i_receive_an_id_of_the_template() throws Throwable {
        assertNotNull(template.getId());
    }

    @Given("^I have a bad template payload$")
    public void i_have_a_bad_template_payload() throws Throwable {
        String badTemplate = "{template : is bad}";
        template = new TemplateDto();
        template.content(badTemplate);
    }

    @When("^I POST the payload to the /templates endpoint$")
    public void i_POST_the_payload_to_the_templates_endpoint() throws Throwable {
        try {
            lastApiResponse = api.templatesPostWithHttpInfo(template);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
    }

    @Given("^I want to fetch all template without an id$")
    public void i_want_to_fetch_all_template_without_an_id() throws Throwable {

    }

    @When("^I GET without an id to the /templates endpoint$")
    public void i_GET_without_an_id_to_the_templates_endpoint() throws Throwable {
        try {
            lastApiResponse = api.templatesGetWithHttpInfo();
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
    }

    @Then("^I receive multiple template payloads$")
    public void i_receive_multiple_template_payloads() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^I want to fetch all templates but DB is empty$")
    public void i_want_to_fetch_all_templates_but_DB_is_empty() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^There is not template in DB$")
    public void there_is_not_template_in_DB() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I GET to the /templates endpoint$")
    public void i_GET_to_the_templates_endpoint() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^I have a template id$")
    public void i_have_a_template_id() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I GET the id to the /templates endpoint$")
    public void i_GET_the_id_to_the_templates_endpoint() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I receive a template payload$")
    public void i_receive_a_template_payload() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^I have an unexisting template id$")
    public void i_have_an_unexisting_template_id() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^I have a request$")
    public void i_have_a_request() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}
