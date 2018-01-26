package io.lozzikit.mail.api.spec.steps;

import com.dumbster.smtp.SimpleSmtpServer;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.lozzikit.mail.ApiException;
import io.lozzikit.mail.ApiResponse;
import io.lozzikit.mail.api.JobApi;
import io.lozzikit.mail.api.MailApi;
import io.lozzikit.mail.api.model.ArchivedMailDto;
import io.lozzikit.mail.api.model.JobDto;
import io.lozzikit.mail.api.model.MailDto;
import io.lozzikit.mail.api.spec.helpers.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class MailSteps {
    // Link to Environment
    private Environment env;

    // Api
    private MailApi mailApi;
    private JobApi jobApi;

    // Mail DTO
    private List<MailDto> mailDtoList;
    private ArchivedMailDto archivedMailDto;
    private int mailId;
    private String uniqueMailfrom;

    // Job DTO
    private JobDto jobDto;
    private int jobId;

    /**
     * Ctor
     *
     * @param environment
     */
    public MailSteps(Environment environment) {
        this.env = environment;
        this.mailApi = env.getMailApi();
        this.jobApi = env.getJobApi();
    }

    /**
     * Default check if mailApi is not null
     */
    @Given("^A mail endpoint$")
    public void A_Mail_Endpoint() {
        assertNotNull(mailApi);
    }

    /**
     * Default check if jobApi is not null
     */
    @And("^A job endpoint$")
    public void A_Job_Endpoint() {
        assertNotNull(jobApi);
    }

    /**
     * Init of server (getter from Environment) and check if not null
     */
    @And("^A SMTP server$")
    public void A_SMTP_Server() {
        SimpleSmtpServer server = Environment.getMockSmtpServer().getWiser();
        assertNotNull(server);
    }

    /**
     * Test of GET on mailApi
     */
    @When("^I GET on the /mails endpoint$")
    public void I_GET_On_The_Mails_Endpoint() {
        try {
            env.setApiResponse(mailApi.mailsGetWithHttpInfo());
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    /**
     * Test of GET on mailApi with given mailId
     */
    @When("^I GET on the /mails/id endpoint$")
    public void I_GET_On_The_Mails_Id_Endpoint() {
        try {
            env.setApiResponse(mailApi.mailsIdGetWithHttpInfo(mailId));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    /**
     * Test of GET on jobApi
     */
    @When("^I GET on the /jobs endpoint$")
    public void I_GET_On_The_Jobs_Endpoint() {
        try {
            env.setApiResponse(jobApi.jobsGetWithHttpInfo());
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    /**
     * Test of GET on jobApi with given jobId
     */
    @When("^I GET on the /jobs/id endpoint$")
    public void I_GET_On_The_Jobs_Id_Endpoint() {
        try {
            env.setApiResponse(jobApi.jobsIdGetWithHttpInfo(jobId));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    /**
     * Test of POST on mailApi with payload
     */
    @When("^I POST the payload to the /mails endpoint$")
    public void I_POST_The_Payload_To_The_Mails_Endpoint() {
        try {
            env.setApiResponse(mailApi.mailsPostWithHttpInfo(mailDtoList));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    /**
     * Test of DELETE on jobApi with jobId
     */
    @When("^I DELETE on the /jobs/id endpoint$")
    public void I_DELETE_On_The_Jobs_Id_Endpoint() {
        try {
            env.setApiResponse(jobApi.jobsIdDeleteWithHttpInfo(jobId));
        } catch (ApiException e) {
            env.setApiException(e);
        }
    }

    /**
     * Test of DELETE on jobApi with jobId
     */
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

    /**
     * mailId = 1
     */
    @Given("^A mail id$")
    public void A_Mail_Id() {
        this.mailId = 1;
    }

    /**
     * mailId = 0
     */
    @Given("^A non-existing mail id$")
    public void A_Non_Existing_Mail_Id() {
        this.mailId = 0;
    }

    /**
     * jobId = 1
     */
    @Given("^A job id$")
    public void A_Job_Id() {
        this.jobId = 1;
    }

    /**
     * jobId = 0
     */
    @Given("^A non-existing job id$")
    public void A_Non_Existing_Job_Id() {
        this.jobId = 0;
    }

    /**
     * jobId = 1
     */
    @Given("^A processed job id$")
    public void A_Processed_Job_Id() {
        this.jobId = 1;
    }

    /**
     * Creating a mail payload
     */
    @Given("^A mail payload$")
    public void A_Mail_Payload() {
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

    /**
     * Creating a mail payload with only 1 mail
     */
    @Given("^A unique mail payload$")
    public void A_Single_Mail_Payload() {
        uniqueMailfrom = "unique." + UUID.randomUUID().toString() + "@a.org";

        this.mailDtoList = new ArrayList<>();

        MailDto mailDto = new MailDto();
        mailDto.setTemplateName("test-template-1");
        mailDto.setFrom(uniqueMailfrom);
        mailDto.addToItem("b.b@b.org");
        mailDto.putMapItem("name", "Olivier");
        this.mailDtoList.add(mailDto);
    }

    /**
     * Receiving a mail payload, check if not null
     */
    @SuppressWarnings("unchecked")
    @And("^I receive a list of mails payload$")
    public void I_Receive_A_List_Of_Mail_Payloads() {
        List<ArchivedMailDto> archivedMails = (List<ArchivedMailDto>) env.getApiResponse().getData();
        assertFalse(archivedMails.isEmpty());
    }

    /**
     * Receiving a job payload, check if not null, only one job
     */
    @And("^I receive a job payload$")
    public void I_Receive_A_Job_Payload() {
        JobDto jobDto = (JobDto) env.getApiResponse().getData();
        assertNotNull(jobDto);
    }

    /**
     * Receiving a job payload, check if not null
     */
    @SuppressWarnings("unchecked")
    @And("^I receive a list of job payload$")
    public void I_Receive_A_List_Of_Job_Payloads() {
        List<JobDto> jobs = (List<JobDto>) env.getApiResponse().getData();
        assertNotNull(jobs);
        assertFalse(jobs.isEmpty());
    }

    /**
     * Receiving an archived mail
     */
    @And("^I receive an archived mail payload$")
    public void I_Receive_An_Archived_Mail_Payload() {
        ArchivedMailDto archivedMailDto = (ArchivedMailDto) env.getApiResponse().getData();
        assertNotNull(archivedMailDto);
    }

    /**
     * Mock server received the mails
     */
    @And("^The SMTP server has received the corresponding mail$")
    public void The_SMTP_Server_Has_Received_The_Corresponding_Mail() {
        SimpleSmtpServer server = Environment.getMockSmtpServer().getWiser();

        // Check that the senders are the same
        assertArrayEquals(mailDtoList.stream()
                        .map(MailDto::getFrom)
                        .sorted()
                        .toArray(),
                server.getReceivedEmails().stream()
                        .map(m -> cleanupString(m.getHeaderValue("From"), " "))
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
                server.getReceivedEmails().stream()
                        .map(m -> cleanupString(m.getHeaderValue("To"), " "))
                        .sorted()
                        .toArray()
        );
    }

    /**
     * The mock SMTP server has not received the mail
     */
    @And("^The SMTP server has not received the unique mail$")
    public void The_SMTP_Server_Has_Not_Received_The_Unique_Mail() {
        SimpleSmtpServer server = Environment.getMockSmtpServer().getWiser();

        assertTrue(server.getReceivedEmails().stream()
                .noneMatch(m -> cleanupString(m.getHeaderValue("To"), " ").equals(uniqueMailfrom)));

    }

    /**
     * String formating
     *
     * @param str string to format
     * @param split string for the spliter
     * @return string formatted
     */
    private String cleanupString(String str, String split) {
        return str.split(split)[0].replace("\"", "");
    }

    /**
     * Test for an invalid job
     */
    @SuppressWarnings("unchecked")
    @Then("^I receive a job with invalid status$")
    public void iReceiveAJobWithInvalidStatus() {
        List<JobDto> jobs = (List<JobDto>) env.getApiResponse().getData();
        JobDto jobDto = jobs.get(0);
        assertEquals("INVALID", jobDto.getStatus());
    }

    /**
     * Test for an invalid mail payload
     */
    @Given("^An invalid mail payload$")
    public void anInvalidMailPayload() {
        final String from = "a.a@a.org";

        this.mailDtoList = new ArrayList<>();

        MailDto mailDto = new MailDto();
        mailDto.setTemplateName("test-template-1");
        mailDto.setFrom(from);
        mailDto.addToItem("c.c@c.org");
        mailDto.putMapItem("thisVariableIsNotInTheTemplate", "Miguel");
        this.mailDtoList.add(mailDto);
    }

    /**
     * Test for a mail with an invalid template
     */
    @Given("^A mail with an invalid template$")
    public void aMailWithAnInvalidTemplate() {
        final String from = "a.a@a.org";

        this.mailDtoList = new ArrayList<>();

        MailDto mailDto = new MailDto();
        mailDto.setTemplateName("thisTemplateDoesntExistLol");
        mailDto.setFrom(from);
        mailDto.addToItem("b.b@b.org");
        mailDto.putMapItem("name", "Sphinx");
        this.mailDtoList.add(mailDto);
    }
}
