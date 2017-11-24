package io.lozzikit.mail.api.spec.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.lozzikit.mail.ApiException;
import io.lozzikit.mail.api.JobApi;
import io.lozzikit.mail.api.MailApi;
import io.lozzikit.mail.api.dto.ArchivedMailDto;
import io.lozzikit.mail.api.dto.JobDto;
import io.lozzikit.mail.api.dto.MailDto;
import io.lozzikit.mail.api.dto.PayloadDto;
import io.lozzikit.mail.api.spec.helpers.Environment;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MailSteps {
    private Environment env;
    private MailApi mailApi;
    private JobApi jobApi;

    private List<MailDto> mailDtoList;
    private ArchivedMailDto archivedMailDto;
    private int mailId;

    private JobDto jobDto;
    private int jobId;

    public MailSteps(Environment environment) {
        this.env = environment;
        this.mailApi = env.getMailApi();
        this.jobApi = env.getJobApi();
    }

    @Given("^A mail endpoint$")
    public void aMailEndpoint() throws Throwable {
        assertNotNull(mailApi);
    }

    @And("^A job endpoint$")
    public void aJobEndpoint() throws Throwable {
        assertNotNull(jobApi);
    }

    @When("^I GET on the /mails endpoint$")
    public void iGETOnTheMailsEndpoint() throws Throwable {
        try {
            env.setApiResponse(mailApi.mailsGetWithHttpInfo());
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @Given("^A filled job and mail database$")
    public void aFilledJobAndMailDatabase() throws Throwable {
        final String from = "z.z@z.org";

        List<MailDto> mailDtoList = new ArrayList<>();

        PayloadDto payloadDto = new PayloadDto();
        payloadDto.setFrom(from);
        payloadDto.addToItem("y.y@y.org");
        payloadDto.putMapItem("firstname", "y");

        MailDto mailDto = new MailDto();
        mailDto.setTemplateId(2);
        mailDto.setPayload(payloadDto);
        mailDtoList.add(mailDto);

        payloadDto = new PayloadDto();
        payloadDto.setFrom(from);
        payloadDto.addToItem("x.x@x.org");
        payloadDto.putMapItem("firstname", "x");

        mailDto = new MailDto();
        mailDto.setTemplateId(2);
        mailDto.setPayload(payloadDto);
        mailDtoList.add(mailDto);

        mailApi.mailsPost(mailDtoList);
    }

    @And("^I receive a list of mails payload$")
    public void iReceiveMultipleMailPayloads() throws Throwable {
        List<ArchivedMailDto> archivedMails = (List<ArchivedMailDto>) env.getApiResponse().getData();
        assertFalse(archivedMails.isEmpty());
    }

    @Given("^A mail payload$")
    public void aMailPayload() throws Throwable {
        final String from = "a.a@a.org";

        this.mailDtoList = new ArrayList<>();

        PayloadDto payloadDto = new PayloadDto();
        payloadDto.setFrom(from);
        payloadDto.addToItem("b.b@b.org");
        payloadDto.putMapItem("firstname", "b");

        MailDto mailDto = new MailDto();
        mailDto.setTemplateId(1);
        mailDto.setPayload(payloadDto);
        this.mailDtoList.add(mailDto);

        payloadDto = new PayloadDto();
        payloadDto.setFrom(from);
        payloadDto.addToItem("c.c@c.org");
        payloadDto.putMapItem("firstname", "c");

        mailDto = new MailDto();
        mailDto.setTemplateId(1);
        mailDto.setPayload(payloadDto);
        this.mailDtoList.add(mailDto);
    }

    @When("^I POST the payload to the /mails endpoint$")
    public void iPOSTThePayloadToTheMailsEndpoint() throws Throwable {
        try {
            env.setApiResponse(mailApi.mailsPostWithHttpInfo(mailDtoList));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @And("^I receive a list of jobs payload$")
    public void iReceiveAListOfJobPayload() throws Throwable {
        List<JobDto> jobs = (List<JobDto>) env.getApiResponse().getData();
        assertNotNull(jobs);
        assertFalse(jobs.isEmpty());
    }

    @Given("^A mail id$")
    public void aMailId() throws Throwable {
        this.mailId = 1;
    }

    @When("^I GET on the /mails/id endpoint$")
    public void iGETOnTheMailsIdEndpoint() throws Throwable {
        try {
            env.setApiResponse(mailApi.mailsIdGetWithHttpInfo(mailId));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @And("^I receive a archived mail payload$")
    public void iReceiveAArchivedMailPayload() throws Throwable {
        ArchivedMailDto archivedMailDto = (ArchivedMailDto) env.getApiResponse().getData();
        assertNotNull(archivedMailDto);
    }

    @Given("^A non-existing mail id$")
    public void aNonExistingMailId() throws Throwable {
        this.mailId = 0;
    }

    @When("^I GET on the /jobs endpoint$")
    public void iGETOnTheJobsEndpoint() throws Throwable {
        try {
            env.setApiResponse(jobApi.jobsGetWithHttpInfo());
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @Given("^A job id$")
    public void aJobId() throws Throwable {
        this.jobId = 1;
    }

    @When("^I GET on the /jobs/id endpoint$")
    public void iGETOnTheJobsIdEndpoint() throws Throwable {
        try {
            env.setApiResponse(jobApi.jobsIdGetWithHttpInfo(jobId));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @And("^I receive a job payload$")
    public void iReceiveAJobPayload() throws Throwable {
        JobDto jobDto = (JobDto) env.getApiResponse().getData();
        assertNotNull(jobDto);
    }

    @Given("^A non-existing job id$")
    public void aNonExistingJobId() throws Throwable {
        this.jobId = 0;
    }

    @When("^I DELETE on the /jobs/id endpoint$")
    public void iDELETEOnTheJobsIdEndpoint() throws Throwable {
        try {
            env.setApiResponse(jobApi.jobsIdDeleteWithHttpInfo(jobId));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @Given("^A processed job id$")
    public void aProcessedJobId() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^An in-progress job id$")
    public void anInProgressJobId() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
