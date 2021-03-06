package io.lozzikit.mail.api.spec.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.lozzikit.mail.ApiException;
import io.lozzikit.mail.api.TemplateApi;
import io.lozzikit.mail.api.model.TemplateDto;
import io.lozzikit.mail.api.spec.helpers.Environment;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class TemplateSteps {
    private static int templateNum = 0;

    // Link to Environment
    private Environment env;

    // Api
    private TemplateApi api;

    // Template DTO
    private TemplateDto templateDto;
    private String templateName;

    /**
     * Ctor
     *
     * @param environment
     */
    public TemplateSteps(Environment environment) {
        this.env = environment;
        this.api = environment.getTemplateApi();
    }

    /**
     * Check if template Api is not null
     */
    @Given("^A template endpoint$")
    public void A_Template_Endpoint() {
        assertNotNull(api);
    }

    /**
     * Test of GET on template Api
     */
    @When("^I GET on the /templates endpoint$")
    public void I_GET_On_The_Templates_Endpoint() {
        try {
            env.setApiResponse(api.templatesGetWithHttpInfo());
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    /**
     * Test of GET on template Api given a template Name
     */
    @When("^I GET on the /templates/name endpoint$")
    public void I_GET_On_The_Templates_Name_Endpoint() {
        try {
            env.setApiResponse(api.templatesNameGetWithHttpInfo(templateName));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    /**
     * Test of POST of a payload on template Api
     */
    @When("^I POST the payload to the /templates endpoint$")
    public void I_POST_The_Payload_To_The_Templates_Endpoint() {
        try {
            env.setApiResponse(api.templatesPostWithHttpInfo(templateDto));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    /**
     * Test of PUT on the template Api with name and template
     */
    @When("^I PUT on the /templates/name endpoint$")
    public void I_PUT_On_The_Templates_Name_Endpoint() {
        try {
            env.setApiResponse(api.templatesNamePutWithHttpInfo(templateName, templateDto));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    /**
     * Test of DELETE on template api given a template name
     */
    @When("^I DELETE on the /templates/name endpoint$")
    public void I_DELETE_On_The_Templates_Name_Endpoint() {
        try {
            env.setApiResponse(api.templatesNameDeleteWithHttpInfo(templateName));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    /**
     * Template name
     */
    @Given("^An existing template name")
    public void A_Template_Name() {
        templateName = "test-template-1";
    }

    /**
     * Template name other
     */
    @Given("^A non-existing template name$")
    public void A_Non_Existing_Template_Name() {
        templateName = "NoTemplateWithThisName";
    }

    /**
     * Receiving a template payload, check if template is not null
     */
    @Then("^I receive a template payload$")
    public void I_Receive_A_Template_Payload() {
        TemplateDto templateDto = (TemplateDto) env.getApiResponse().getData();
        assertNotNull(templateDto);
        assertNotNull(templateDto.getName());
        assertNotNull(templateDto.getUrl());
        assertNotNull(templateDto.getDescription());
        assertNotNull(templateDto.getContent());
    }

    /**
     * Receiving multiple template, check if not empty
     */
    @SuppressWarnings("unchecked")
    @Then("^I receive multiple template payloads$")
    public void I_Receive_Multiple_Template_Payloads() {
        List<TemplateDto> templates = (List<TemplateDto>) env.getApiResponse().getData();
        assertFalse(templates.isEmpty());
    }

    /**
     * Creates a template payload with one template. Valid
     */
    @Given("^A template payload$")
    public void A_Template_Payload() {
        templateDto = new TemplateDto();
        templateDto.setName("test-template-1");
        templateDto.setDescription("A templateDto for testing using cucumber");
        templateDto.setContent("Subject:A test subject\n" +
                "\n" +
                "Hello ${name}");
    }

    /**
     * Creates a template payload with one template. Invalid
     */
    @Given("^A bad template payload$")
    public void A_Bad_Template_Payload() {
        templateDto = new TemplateDto();
        templateDto.setName("test-templateDto-" + ++templateNum);
        templateDto.setDescription("A templateDto for testing using cucumber");
        templateDto.setContent("Hello ${name");
    }
}
