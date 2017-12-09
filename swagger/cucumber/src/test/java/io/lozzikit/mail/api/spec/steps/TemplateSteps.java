package io.lozzikit.mail.api.spec.steps;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.lozzikit.mail.ApiException;
import io.lozzikit.mail.api.TemplateApi;
import io.lozzikit.mail.api.dto.TemplateDto;
import io.lozzikit.mail.api.spec.helpers.Environment;

import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.Assert.assertEquals;
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

    @Given("^An empty template database$")
    public void an_empty_template_database() throws Throwable {
        Request request = new Request.Builder()
                .url(env.getTestUrl("/tests/templates"))
                .delete()
                .build();
        assertEquals(200, env.executeTestRequest(request).code());
    }

    @Given("^A template endpoint$")
    public void aTemplateEndpoint() throws Throwable {
        assertNotNull(api);
    }

    @When("^I GET on the /templates endpoint$")
    public void iGETOnTheTemplatesEndpoint() throws Throwable {
        try {
            env.setApiResponse(api.templatesGetWithHttpInfo());
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @Given("^A filled template database$")
    public void aFilledDatabase() throws Throwable {
        an_empty_template_database();

        Request request = new Request.Builder()
                .url(env.getTestUrl("/tests/templates"))
                .post(RequestBody.create(env.JSON, "default"))
                .build();
        assertEquals(200, env.executeTestRequest(request).code());
    }

    @SuppressWarnings("unchecked")
    @Then("^I receive multiple template payloads$")
    public void iReceiveMultipleTemplatePayloads() throws Throwable {
        List<TemplateDto> templates = (List<TemplateDto>) env.getApiResponse().getData();
        assertFalse(templates.isEmpty());
    }

    @Given("^An existing template name")
    public void aTemplateId() throws Throwable {
        templateName = "test-template-1";
    }

    @When("^I GET on the /templates/name endpoint$")
    public void iGETOnTheTemplatesIdEndpoint() throws Throwable {
        try {
            env.setApiResponse(api.templatesNameGetWithHttpInfo(templateName));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @Then("^I receive a template payload$")
    public void iReceiveATemplatePayload() throws Throwable {
        TemplateDto templateDto = (TemplateDto) env.getApiResponse().getData();
        assertNotNull(templateDto);
        assertNotNull(templateDto.getUrl());
        assertNotNull(templateDto.getName());
        assertNotNull(templateDto.getDescription());
        assertNotNull(templateDto.getContent());
    }

    @Given("^A template payload$")
    public void aTemplatePayload() throws Throwable {
        templateDto = new TemplateDto();
        templateDto.setName("test-template-payload");
        templateDto.setDescription("A templateDto for testing using cucumber");
        templateDto.setContent("Hello <p th:text=\"#{name}\">Madam/Sir</p>");
    }

    @When("^I POST the payload to the /templates endpoint$")
    public void iPOSTThePayloadToTheTemplatesEndpoint() throws Throwable {
        try {
            env.setApiResponse(api.templatesPostWithHttpInfo(templateDto));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @Given("^I have a bad template payload$")
    public void iHaveABadTemplatePayload() throws Throwable {
        templateDto = new TemplateDto();
        templateDto.setName("test-templateDto-" + ++templateNum);
        templateDto.setDescription("A templateDto for testing using cucumber");
        templateDto.setContent("Hello <p th:text=\"#{name");
    }

    @Given("^A non-existing template name$")
    public void aNonExistingTemplateId() throws Throwable {
        templateName = "NoTemplateWithThisName";
    }

    @When("^I PUT on the /templates/name endpoint$")
    public void iPUTOnTheTemplatesIdEndpoint() throws Throwable {
        try {
            env.setApiResponse(api.templatesNamePutWithHttpInfo(templateName, templateDto));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @When("^I DELETE on the /templates/name endpoint$")
    public void iDELETEOnTheTemplatesIdEndpoint() throws Throwable {
        try {
            env.setApiResponse(api.templatesNameDeleteWithHttpInfo(templateName));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }
}
