package io.lozzikit.mail.api.spec.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.lozzikit.mail.ApiException;
import io.lozzikit.mail.ApiResponse;
import io.lozzikit.mail.api.TemplateApi;
import io.lozzikit.mail.api.dto.TemplateDto;
import io.lozzikit.mail.api.spec.helpers.Environment;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Olivier Liechti on 27/07/17.
 */
public class CreationSteps {
    private Environment environment;
    private TemplateApi api;

    private TemplateDto templateDto;
    private int templateId;

    private ApiResponse apiResponse;
    private ApiException apiException;
    private boolean apiCallThrewException;
    private int apiStatusCode;

    public CreationSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("^A template endpoint$")
    public void a_template_endpoint() throws Throwable {
        assertNotNull(api);
    }

    @Given("^An empty database$")
    public void an_empty_database() throws Throwable {
    }

    @When("^I GET on the /templates endpoint$")
    public void i_GET_on_the_templates_endpoint() throws Throwable {
        try {
            apiResponse = api.templatesGetWithHttpInfo();
            apiCallThrewException = false;
            apiException = null;
            apiStatusCode = apiResponse.getStatusCode();
        } catch (ApiException e) {
            reset(e);
        }
    }

    @Then("^I receive a (\\d+) status code$")
    public void i_receive_a_status_code(int status) throws Throwable {
        assertEquals(status, apiStatusCode);
    }

    @Given("^A filled database$")
    public void a_filled_database() throws Throwable {
        TemplateDto templateDto = new TemplateDto();
        templateDto.setName("test-templateDto");
        templateDto.setDescription("A templateDto for testing using cucumber");
        templateDto.setContent("Hello ${name}!");
        api.templatesPost(templateDto);
        templateDto.setName("test-templateDto-2");
        api.templatesPost(templateDto);
    }

    @Then("^I receive multiple template payloads$")
    public void i_receive_multiple_template_payloads() throws Throwable {
        List<TemplateDto> templates = (List<TemplateDto>) apiResponse.getData();
        assertFalse(templates.isEmpty());
    }

    @Given("^A template id$")
    public void a_template_id() throws Throwable {
        templateId = 1;
    }

    @When("^I GET on the /templates/id endpoint$")
    public void i_GET_on_the_templates_id_endpoint() throws Throwable {
        try {
            apiResponse = api.templatesIdGetWithHttpInfo(templateId);
            apiCallThrewException = false;
            apiException = null;
            apiStatusCode = apiResponse.getStatusCode();
        } catch (ApiException e) {
            reset(e);
        }
    }

    @Then("^I receive a template payload$")
    public void i_receive_a_template_payload() throws Throwable {
        TemplateDto templateDto = (TemplateDto) apiResponse.getData();
        assertNotNull(templateDto);
    }

    @Given("^A template payload$")
    public void a_template_payload() throws Throwable {
        templateDto = new TemplateDto();
        templateDto.setName("test-templateDto");
        templateDto.setDescription("A templateDto for testing using cucumber");
        templateDto.setContent("Hello ${name}!");
    }

    @When("^I POST the payload to the /templates endpoint$")
    public void i_POST_the_payload_to_the_templates_endpoint() throws Throwable {
        try {
            apiResponse = api.templatesPostWithHttpInfo(templateDto);
            apiCallThrewException = false;
            apiException = null;
            apiStatusCode = apiResponse.getStatusCode();
        } catch (ApiException e) {
            reset(e);
        }
    }

    @Given("^I have a bad template payload$")
    public void i_have_a_bad_template_payload() throws Throwable {
        templateDto = new TemplateDto();
        templateDto.setName("test-templateDto");
        templateDto.setDescription("A templateDto for testing using cucumber");
        templateDto.setContent("Hello ${");
    }

    @Given("^A non-existing template id$")
    public void a_non_existing_template_id() throws Throwable {
        templateId = 0;
    }

    private void reset(ApiException e) {
        apiCallThrewException = true;
        apiResponse = null;
        apiException = e;
        apiStatusCode = e.getCode();
    }
}
