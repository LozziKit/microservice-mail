package io.lozzikit.mail.api.spec.steps;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.lozzikit.mail.ApiClient;
import io.lozzikit.mail.ApiException;
import io.lozzikit.mail.api.JobApi;
import io.lozzikit.mail.api.MailApi;
import io.lozzikit.mail.api.dto.ArchivedMailDto;
import io.lozzikit.mail.api.dto.JobDto;
import io.lozzikit.mail.api.dto.MailDto;
import io.lozzikit.mail.api.spec.helpers.Environment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

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

    @Given("^An empty job and mail database")
    public void an_empty_job_and_mail_database() throws Throwable {
        Request request = new Request.Builder()
                .url(env.getTestUrl("/tests/mails+jobs"))
                .delete()
                .build();
        assertEquals(200, env.executeTestRequest(request).code());
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
        an_empty_job_and_mail_database();

        Request request = new Request.Builder()
                .url(env.getTestUrl("/tests/mails+jobs"))
                .post(RequestBody.create(env.JSON, "default"))
                .build();
        assertEquals(200, env.executeTestRequest(request).code());
    }

    @SuppressWarnings("unchecked")
    @And("^I receive a list of mails payload$")
    public void iReceiveMultipleMailPayloads() throws Throwable {
        List<ArchivedMailDto> archivedMails = (List<ArchivedMailDto>) env.getApiResponse().getData();
        assertFalse(archivedMails.isEmpty());
    }

    @Given("^A mail payload$")
    public void aMailPayload() throws Throwable {
        final String from = "a.a@a.org";

        this.mailDtoList = new ArrayList<>();

        MailDto mailDto = new MailDto();
        mailDto.setTemplateName("test-template-1");
        mailDto.setFrom(from);
        mailDto.addToItem("b.b@b.org");
        mailDto.putMapItem("firstname", "b");
        this.mailDtoList.add(mailDto);

        mailDto = new MailDto();
        mailDto.setTemplateName("test-template-1");
        mailDto.setFrom(from);
        mailDto.addToItem("c.c@c.org");
        mailDto.putMapItem("firstname", "c");
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

    @SuppressWarnings("unchecked")
    @And("^I receive a list of jobs payload$")
    public void iReceiveAListOfJobPayload() throws Throwable {
        List<JobDto> jobs = (List<JobDto>) env.getApiResponse().getData();
        assertNotNull(jobs);
        assertFalse(jobs.isEmpty());
    }

    @Given("^A mail id$")
    public void aMailId() throws Throwable {
        Request request = new Request.Builder()
                .url(env.getTestUrl("/tests/mails/one"))
                .get()
                .build();
        Response res = env.executeTestRequest(request);
        assertEquals(200, res.code());
        this.mailId = Integer.parseInt(res.body().string());
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
        this.jobId = 1;
    }

    @Given("^An in-progress job id$")
    public void anInProgressJobId() throws Throwable {
        this.jobId = 4;
    }
}
