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
public class TemplateSteps {
    private Environment env;
    private TemplateApi api;

    private TemplateDto templateDto;
    private int templateId;

    public TemplateSteps(Environment environment) {
        this.env = environment;
        this.api = environment.getTemplateApi();
    }

    @Given("^A template endpoint$")
    public void a_template_endpoint() throws Throwable {
        assertNotNull(api);
    }

    @When("^I GET on the /templates endpoint$")
    public void i_GET_on_the_templates_endpoint() throws Throwable {
        try {
            env.setApiResponse(api.templatesGetWithHttpInfo());
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @Given("^A filled template database$")
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
        List<TemplateDto> templates = (List<TemplateDto>) env.getApiResponse().getData();
        assertFalse(templates.isEmpty());
    }

    @Given("^A template id$")
    public void a_template_id() throws Throwable {
        templateId = 1;
    }

    @When("^I GET on the /templates/id endpoint$")
    public void i_GET_on_the_templates_id_endpoint() throws Throwable {
        try {
            env.setApiResponse(api.templatesIdGetWithHttpInfo(templateId));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @Then("^I receive a template payload$")
    public void i_receive_a_template_payload() throws Throwable {
        TemplateDto templateDto = (TemplateDto) env.getApiResponse().getData();
        assertNotNull(templateDto);
    }

    @Given("^A template payload$")
    public void a_template_payload() throws Throwable {
        templateDto = new TemplateDto();
        templateDto.setName("test-templateDto");
        templateDto.setDescription("A templateDto for testing using cucumber");
        templateDto.setContent("Hello <p th:text=\"#{name}\">Madam/Sir</p>");
    }

    @When("^I POST the payload to the /templates endpoint$")
    public void i_POST_the_payload_to_the_templates_endpoint() throws Throwable {
        try {
            env.setApiResponse(api.templatesPostWithHttpInfo(templateDto));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @Given("^I have a bad template payload$")
    public void i_have_a_bad_template_payload() throws Throwable {
        templateDto = new TemplateDto();
        templateDto.setName("test-templateDto");
        templateDto.setDescription("A templateDto for testing using cucumber");
        templateDto.setContent("Hello <p th:text=\"#{name");
    }

    @Given("^A non-existing template id$")
    public void a_non_existing_template_id() throws Throwable {
        templateId = 0;
    }

    @When("^I PUT on the /templates/id endpoint$")
    public void iPUTOnTheTemplatesIdEndpoint() throws Throwable {
        try {
            env.setApiResponse(api.templatesIdPutWithHttpInfo(templateId, templateDto));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @When("^I DELETE on the /templates/id endpoint$")
    public void iDELETEOnTheTemplatesIdEndpoint() throws Throwable {
        try {
            env.setApiResponse(api.templatesIdDeleteWithHttpInfo(templateId));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }
}
