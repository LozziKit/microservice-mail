package io.lozzikit.mail.api.spec.steps;

import com.squareup.okhttp.Request;
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

    /*
    @Given("^An empty job and mail database")
    public void anEmptyJobAndMailDatabase() throws Throwable {
        Request request = new Request.Builder()
            .url(env.getTestUrl("/tests/mails+jobs"))
            .delete()
            .build();
        assertEquals(200, env.executeRequest(request).code());
    }
    */
    /*
    @Given("^A filled job and mail database$")
    public void aFilledJobAndMailDatabase() throws Throwable {
        anEmptyJobAndMailDatabase();

        Request request = new Request.Builder()
            .url(env.getTestUrl("/tests/mails+jobs"))
            .post(RequestBody.create(env.JSON, "default"))
            .build();
        assertEquals(200, env.executeRequest(request).code());
    }
    */

    @Given("^A mail endpoint$")
    public void A_Mail_Endpoint() throws Throwable {
        assertNotNull(mailApi);
    }

    @And("^A job endpoint$")
    public void A_Job_Endpoint() throws Throwable {
        assertNotNull(jobApi);
    }

    @And("^A SMTP server$")
    public void A_SMTP_Server() throws Throwable {
        Wiser wiser = Environment.getMockSmtpServer().getWiser();
        assertNotNull(wiser);
    }

    @When("^I GET on the /mails endpoint$")
    public void I_GET_On_The_Mails_Endpoint() throws Throwable {
        try {
            env.setApiResponse(mailApi.mailsGetWithHttpInfo());
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @When("^I GET on the /mails/id endpoint$")
    public void I_GET_On_The_Mails_Id_Endpoint() throws Throwable {
        try {
            env.setApiResponse(mailApi.mailsIdGetWithHttpInfo(mailId));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @When("^I GET on the /jobs endpoint$")
    public void I_GET_On_The_Jobs_Endpoint() throws Throwable {
        try {
            env.setApiResponse(jobApi.jobsGetWithHttpInfo());
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @When("^I GET on the /jobs/id endpoint$")
    public void I_GET_On_The_Jobs_Id_Endpoint() throws Throwable {
        try {
            env.setApiResponse(jobApi.jobsIdGetWithHttpInfo(jobId));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @When("^I POST the payload to the /mails endpoint$")
    public void I_POST_The_Payload_To_The_Mails_Endpoint() throws Throwable {
        try {
            env.setApiResponse(mailApi.mailsPostWithHttpInfo(mailDtoList));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @When("^I DELETE on the /jobs/id endpoint$")
    public void I_DELETE_On_The_Jobs_Id_Endpoint() throws Throwable {
        try {
            env.setApiResponse(jobApi.jobsIdDeleteWithHttpInfo(jobId));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @And("^I DELETE the job on the /jobs/id endpoint$")
    public void I_DELETE_The_Job_On_The_Jobs_Id_Endpoint() throws Throwable {
        List<JobDto> jobs = (List<JobDto>) env.getApiResponse().getData();

        Request request = new Request.Builder()
                .url(env.getServerUrl(jobs.get(0).getUrl()))
                .delete()
                .build();

        Response res = env.executeRequest(request);
        env.setApiResponse(new ApiResponse(res.code(), res.headers().toMultimap()));
    }

    @Given("^A mail id$")
    public void A_Mail_Id() throws Throwable {
        Request request = new Request.Builder()
                .url(env.getTestUrl("/tests/mails/one"))
                .get()
                .build();
        Response res = env.executeRequest(request);
        assertEquals(200, res.code());
        this.mailId = Integer.parseInt(res.body().string());
    }

    @Given("^A non-existing mail id$")
    public void A_Non_Existing_Mail_Id() throws Throwable {
        this.mailId = 0;
    }

    @Given("^A job id$")
    public void A_Job_Id() throws Throwable {
        Request request = new Request.Builder()
                .url(env.getTestUrl("/tests/jobs/one"))
                .get()
                .build();
        Response res = env.executeRequest(request);
        assertEquals(200, res.code());
        this.jobId = Integer.parseInt(res.body().string());
    }

    @Given("^A non-existing job id$")
    public void A_Non_Existing_Job_Id() throws Throwable {
        this.jobId = 0;
    }

    @Given("^A processed job id$")
    public void A_Processed_Job_Id() throws Throwable {
        Request request = new Request.Builder()
                .url(env.getTestUrl("/tests/jobs/one?status=DONE"))
                .get()
                .build();
        Response res = env.executeRequest(request);
        assertEquals(200, res.code());
        this.jobId = Integer.parseInt(res.body().string());
    }

    @Given("^An in-progress job id$")
    public void An_In_Progress_Job_Id() throws Throwable {
        Request request = new Request.Builder()
                .url(env.getTestUrl("/tests/jobs/one?status=ONGOING"))
                .get()
                .build();
        Response res = env.executeRequest(request);
        assertEquals(200, res.code());
        this.jobId = Integer.parseInt(res.body().string());
    }

    @Given("^A mail payload$")
    public void A_Mail_Payload() throws Throwable {
        final String from = "a.a@a.org";

        this.mailDtoList = new ArrayList<>();

        MailDto mailDto = new MailDto();
        mailDto.setTemplateName("test-template-1");
        mailDto.setFrom(from);
        mailDto.addToItem("b.b@b.org");
        mailDto.putMapItem("name", "Sphinx");
        this.mailDtoList.add(mailDto);

        mailDto = new MailDto();
        mailDto.setTemplateName("test-template-1");
        mailDto.setFrom(from);
        mailDto.addToItem("c.c@c.org");
        mailDto.putMapItem("name", "Miguel");
        this.mailDtoList.add(mailDto);
    }

    @Given("^A unique mail payload$")
    public void A_Single_Mail_Payload() throws Throwable {
        uniqueMailfrom = "unique." + UUID.randomUUID().toString() + "@a.org";

        this.mailDtoList = new ArrayList<>();

        MailDto mailDto = new MailDto();
        mailDto.setTemplateName("test-template-1");
        mailDto.setFrom(uniqueMailfrom);
        mailDto.addToItem("b.b@b.org");
        mailDto.putMapItem("name", "Olivier");
        this.mailDtoList.add(mailDto);
    }

    @SuppressWarnings("unchecked")
    @And("^I receive a list of mails payload$")
    public void I_Receive_A_List_Of_Mail_Payloads() throws Throwable {
        List<ArchivedMailDto> archivedMails = (List<ArchivedMailDto>) env.getApiResponse().getData();
        assertFalse(archivedMails.isEmpty());
    }


    @And("^I receive a job payload$")
    public void I_Receive_A_Job_Payload() throws Throwable {
        JobDto jobDto = (JobDto) env.getApiResponse().getData();
        assertNotNull(jobDto);
    }

    @SuppressWarnings("unchecked")
    @And("^I receive a list of job payload$")
    public void I_Receive_A_List_Of_Job_Payloads() throws Throwable {
        List<JobDto> jobs = (List<JobDto>) env.getApiResponse().getData();
        assertNotNull(jobs);
        assertFalse(jobs.isEmpty());
    }

    @And("^I receive an archived mail payload$")
    public void I_Receive_An_Archived_Mail_Payload() throws Throwable {
        ArchivedMailDto archivedMailDto = (ArchivedMailDto) env.getApiResponse().getData();
        assertNotNull(archivedMailDto);
    }

    @And("^The SMTP server has received the corresponding mail$")
    public void The_SMTP_Server_Has_Received_The_Corresponding_Mail() throws Throwable {
        Wiser wiser = Environment.getMockSmtpServer().getWiser();

        // Check that the senders are the same
        assertArrayEquals(mailDtoList.stream()
                        .map(MailDto::getFrom)
                        .sorted()
                        .toArray(),
                wiser.getMessages().stream()
                        .map(WiserMessage::getEnvelopeSender)
                        .sorted()
                        .toArray()
        );

        // Check that the receivers are the same.
        assertArrayEquals(mailDtoList.stream()
                        .flatMap(m -> Stream.of(
                                m.getTo().stream(),
                                m.getCc().stream(),
                                m.getCci().stream()
                        ).flatMap(i -> i))
                        .sorted()
                        .toArray(),
                wiser.getMessages().stream()
                        .map(WiserMessage::getEnvelopeReceiver)
                        .sorted()
                        .toArray()
        );
    }

    @And("^The SMTP server has not received the unique mail$")
    public void The_SMTP_Server_Has_Not_Received_The_Unique_Mail() throws Throwable {
        Wiser wiser = Environment.getMockSmtpServer().getWiser();

        assertTrue(wiser.getMessages().stream()
                .noneMatch(m -> m.getEnvelopeSender().equals(uniqueMailfrom)));

    }
}
