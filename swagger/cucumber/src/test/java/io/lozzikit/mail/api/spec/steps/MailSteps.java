package io.lozzikit.mail.api.spec.steps;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.lozzikit.mail.ApiException;
import io.lozzikit.mail.ApiResponse;
import io.lozzikit.mail.api.JobApi;
import io.lozzikit.mail.api.MailApi;
import io.lozzikit.mail.api.dto.ArchivedMailDto;
import io.lozzikit.mail.api.dto.JobDto;
import io.lozzikit.mail.api.dto.MailDto;
import io.lozzikit.mail.api.spec.helpers.Environment;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class MailSteps {
    private Environment env;
    private MailApi mailApi;
    private JobApi jobApi;

    private List<MailDto> mailDtoList;
    private ArchivedMailDto archivedMailDto;
    private int mailId;
    private String uniqueMailfrom;

    private JobDto jobDto;
    private int jobId;

    public MailSteps(Environment environment) {
        this.env = environment;
        this.mailApi = env.getMailApi();
        this.jobApi = env.getJobApi();
    }

    @Given("^An empty job and mail database")
    public void anEmptyJobAndMailDatabase() throws Throwable {
        Request request = new Request.Builder()
            .url(env.getTestUrl("/tests/mails+jobs"))
            .delete()
            .build();
        assertEquals(200, env.executeRequest(request).code());
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
        anEmptyJobAndMailDatabase();

        Request request = new Request.Builder()
            .url(env.getTestUrl("/tests/mails+jobs"))
            .post(RequestBody.create(env.JSON, "default"))
            .build();
        assertEquals(200, env.executeRequest(request).code());
    }

    @SuppressWarnings("unchecked")
    @And("^I receive a list of mails payload$")
    public void iReceiveMultipleMailPayloads() throws Throwable {
        List<ArchivedMailDto> archivedMails = (List<ArchivedMailDto>) env.getApiResponse().getData();
        assertFalse(archivedMails.isEmpty());
    }

    @Given("^A unique mail payload$")
    public void aSingleMailPayload() throws Throwable {
        uniqueMailfrom = "unique." + UUID.randomUUID().toString()  + "@a.org";

        this.mailDtoList = new ArrayList<>();

        MailDto mailDto = new MailDto();
        mailDto.setTemplateName("test-template-1");
        mailDto.setFrom(uniqueMailfrom);
        mailDto.addToItem("b.b@b.org");
        mailDto.putMapItem("firstname", "b");
        this.mailDtoList.add(mailDto);
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
        Response res = env.executeRequest(request);
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
        Request request = new Request.Builder()
                .url(env.getTestUrl("/tests/jobs/one"))
                .get()
                .build();
        Response res = env.executeRequest(request);
        assertEquals(200, res.code());
        this.jobId = Integer.parseInt(res.body().string());
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
        Request request = new Request.Builder()
                .url(env.getTestUrl("/tests/jobs/one?status=DONE"))
                .get()
                .build();
        Response res = env.executeRequest(request);
        assertEquals(200, res.code());
        this.jobId = Integer.parseInt(res.body().string());
    }

    @Given("^An in-progress job id$")
    public void anInProgressJobId() throws Throwable {
        Request request = new Request.Builder()
                .url(env.getTestUrl("/tests/jobs/one?status=ONGOING"))
                .get()
                .build();
        Response res = env.executeRequest(request);
        assertEquals(200, res.code());
        this.jobId = Integer.parseInt(res.body().string());
    }

    @And("^A SMTP server$")
    public void aSMTPServer() throws Throwable {
        Wiser wiser = Environment.getMockSmtpServer().getWiser();
        assertNotNull(wiser);
    }

    @And("^I wait (\\d+) seconds$")
    public void iWaitSeconds(int seconds) throws Throwable {
        Thread.sleep(seconds * 1000);
    }

    @And("^The SMTP server has received the corresponding mail$")
    public void theSMTPServerHasReceivedTheCorrespondingMail() throws Throwable {
        Wiser wiser = Environment.getMockSmtpServer().getWiser();

        // Check that the senders are the same
        assertArrayEquals(
                mailDtoList
                        .stream()
                        .map(MailDto::getFrom)
                        .sorted()
                        .toArray(),
                wiser.getMessages()
                        .stream()
                        .map(WiserMessage::getEnvelopeSender)
                        .sorted()
                        .toArray()
        );

        // Check that the receivers are the same.
        assertArrayEquals(
                mailDtoList
                        .stream()
                        .flatMap(m -> Stream.of(
                                m.getTo().stream(),
                                m.getCc().stream(),
                                m.getCci().stream()
                            ).flatMap(i -> i))
                        .sorted()
                        .toArray(),
                wiser.getMessages()
                        .stream()
                        .map(WiserMessage::getEnvelopeReceiver)
                        .sorted()
                        .toArray()
        );
    }

    @And("^I DELETE the job on the /jobs/id endpoint$")
    public void iDELETETheJobOnTheJobsIdEndpoint() throws Throwable {
        List<JobDto> jobs = (List<JobDto>) env.getApiResponse().getData();

        Request request = new Request.Builder()
                .url(env.getServerUrl(jobs.get(0).getUrl()))
                .delete()
                .build();

        Response res = env.executeRequest(request);
        env.setApiResponse(new ApiResponse(res.code(), res.headers().toMultimap()));
    }

    @And("^The SMTP server has not received the unique mail$")
    public void theSMTPServerHasNotReceivedTheCorrespondingMail() throws Throwable {
        Wiser wiser = Environment.getMockSmtpServer().getWiser();

        assertTrue(wiser.getMessages()
                .stream()
                .noneMatch(m -> m.getEnvelopeSender().equals(uniqueMailfrom)));

    }
}
