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

    private Environment env;
    private TemplateApi api;
    private TemplateDto templateDto;
    private String templateName;

    public TemplateSteps(Environment environment) {
        this.env = environment;
        this.api = environment.getTemplateApi();
    }

    @Given("^A template endpoint$")
    public void A_Template_Endpoint() throws Throwable {
        assertNotNull(api);
    }

    @When("^I GET on the /templates endpoint$")
    public void I_GET_On_The_Templates_Endpoint() throws Throwable {
        try {
            env.setApiResponse(api.templatesGetWithHttpInfo());
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @When("^I GET on the /templates/name endpoint$")
    public void I_GET_On_The_Templates_Name_Endpoint() throws Throwable {
        try {
            env.setApiResponse(api.templatesNameGetWithHttpInfo(templateName));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @When("^I POST the payload to the /templates endpoint$")
    public void I_POST_The_Payload_To_The_Templates_Endpoint() throws Throwable {
        try {
            env.setApiResponse(api.templatesPostWithHttpInfo(templateDto));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @When("^I PUT on the /templates/name endpoint$")
    public void I_PUT_On_The_Templates_Name_Endpoint() throws Throwable {
        try {
            env.setApiResponse(api.templatesNamePutWithHttpInfo(templateName, templateDto));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @When("^I DELETE on the /templates/name endpoint$")
    public void I_DELETE_On_The_Templates_Name_Endpoint() throws Throwable {
        try {
            env.setApiResponse(api.templatesNameDeleteWithHttpInfo(templateName));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @Given("^An existing template name")
    public void A_Template_Name() throws Throwable {
        templateName = "test-template-1";
    }

    @Given("^A non-existing template name$")
    public void A_Non_Existing_Template_Name() throws Throwable {
        templateName = "NoTemplateWithThisName";
    }

    @Then("^I receive a template payload$")
    public void I_Receive_A_Template_Payload() throws Throwable {
        TemplateDto templateDto = (TemplateDto) env.getApiResponse().getData();
        assertNotNull(templateDto);
        assertNotNull(templateDto.getName());
        assertNotNull(templateDto.getUrl());
        assertNotNull(templateDto.getDescription());
        assertNotNull(templateDto.getContent());
    }

    @SuppressWarnings("unchecked")
    @Then("^I receive multiple template payloads$")
    public void I_Receive_Multiple_Template_Payloads() throws Throwable {
        List<TemplateDto> templates = (List<TemplateDto>) env.getApiResponse().getData();
        assertFalse(templates.isEmpty());
    }

    @Given("^A template payload$")
    public void A_Template_Payload() throws Throwable {
        templateDto = new TemplateDto();
        templateDto.setName("test-template-1");
        templateDto.setDescription("A templateDto for testing using cucumber");
        templateDto.setContent("Subject:A test subject\n" +
                "\n" +
                "Hello ${name}");
    }

    @Given("^A bad template payload$")
    public void A_Bad_Template_Payload() throws Throwable {
        templateDto = new TemplateDto();
        templateDto.setName("test-templateDto-" + ++templateNum);
        templateDto.setDescription("A templateDto for testing using cucumber");
        templateDto.setContent("Hello ${name");
    }
}
