package io.lozzikit.mail.api.spec.helpers;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import io.lozzikit.mail.ApiException;
import io.lozzikit.mail.ApiResponse;
import io.lozzikit.mail.api.JobApi;
import io.lozzikit.mail.api.MailApi;
import io.lozzikit.mail.api.TemplateApi;
import io.lozzikit.mail.api.spec.smtp.MockSmtpServer;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Olivier Liechti on 24/06/17.
 */
public class Environment {
    // Smtp server
    private static MockSmtpServer mockSmtpServer = new MockSmtpServer();
    public final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    // Api
    private TemplateApi templateApi = new TemplateApi();
    private MailApi mailApi = new MailApi();
    private JobApi jobApi = new JobApi();
    private OkHttpClient client = new OkHttpClient();
    private String baseServerUrl;
    private String serverContextPath;
    private String baseTestUrl;

    // Response
    private ApiResponse apiResponse;
    private ApiException apiException;
    private boolean apiCallThrewException;

    /**
     * Ctor
     *
     * @throws IOException
     */
    public Environment() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));

        baseServerUrl = properties.getProperty("io.lozzikit.mail.server.url");
        serverContextPath = baseServerUrl + properties.getProperty("io.lozzikit.mail.server.contextPath");
        templateApi.getApiClient().setBasePath(serverContextPath);
        mailApi.getApiClient().setBasePath(serverContextPath);
        jobApi.getApiClient().setBasePath(serverContextPath);

        baseTestUrl = properties.getProperty("io.lozzikit.mail.server-test.url");
    }

    /**
     * Basic Getter for the Mock SMTP Server
     *
     * @return MockSmtpServer
     */
    public static MockSmtpServer getMockSmtpServer() {
        return mockSmtpServer;
    }

    /**
     * Getter for the template API
     *
     * @return TemplateApi
     */
    public TemplateApi getTemplateApi() {
        return templateApi;
    }

    /**
     * Getter for the mail API
     *
     * @return MailApi
     */
    public MailApi getMailApi() {
        return mailApi;
    }

    /**
     * Getter for the JobApi
     *
     * @return JobApi
     */
    public JobApi getJobApi() {
        return jobApi;
    }

    /**
     * Executes a given request and sends back a response
     *
     * @param request request to execute
     * @return Response response to given request
     * @throws IOException
     */
    public Response executeRequest(Request request) throws IOException {
        return client.newCall(request).execute();
    }

    /**
     * Getter for Server URL given a resource path
     *
     * @param path of the resource to access
     * @return String address of the server
     */
    public String getServerUrl(String path) {
        return baseServerUrl + path;
    }

    /**
     * Getter for the test Server URL given a resource path
     *
     * @param path of the ressource to acces in test server
     * @return String address of the server
     */
    public String getTestUrl(String path) {
        return baseTestUrl + path;
    }

    /**
     * Getter for the Api Response
     * @return ApiResponse
     */
    public ApiResponse getApiResponse() {
        return apiResponse;
    }

    /**
     * Setter for Api Response
     *
     * @param apiResponse
     */
    public void setApiResponse(ApiResponse apiResponse) {
        this.apiResponse = apiResponse;
        this.apiException = null;
        this.apiCallThrewException = false;
    }

    /**
     * Getter for exception in Api Response
     *
     * @return ApiException
     */
    public ApiException getApiException() {
        return apiException;
    }

    /**
     * Setter for exception in Api Response
     *
     * @param apiException
     */
    public void setApiException(ApiException apiException) {
        this.apiException = apiException;
        this.apiResponse = null;
        // Sets the boolean to true if exception
        this.apiCallThrewException = true;
    }

    /**
     * Getter for boolean if exception happended
     * @return
     */
    public boolean isApiCallThrewException() {
        return apiCallThrewException;
    }

    /**
     * Returns the error code of the Api
     *
     * @return int error code
     */
    public int getApiStatusCode() {
        return (apiCallThrewException) ? apiException.getCode() : apiResponse.getStatusCode();
    }
}
