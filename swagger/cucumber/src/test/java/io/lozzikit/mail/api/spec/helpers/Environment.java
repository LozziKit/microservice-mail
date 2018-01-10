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
    // Api
    private TemplateApi templateApi = new TemplateApi();
    private MailApi mailApi = new MailApi();
    private JobApi jobApi = new JobApi();
    private OkHttpClient client = new OkHttpClient();
    private String baseServerUrl;
    private String baseTestUrl;

    public final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    // Response
    private ApiResponse apiResponse;
    private ApiException apiException;
    private boolean apiCallThrewException;

    // Smtp server
    private static MockSmtpServer mockSmtpServer = new MockSmtpServer();

    public Environment() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));

        baseServerUrl = properties.getProperty("io.lozzikit.mail.server.url");
        templateApi.getApiClient().setBasePath(baseServerUrl);
        mailApi.getApiClient().setBasePath(baseServerUrl);
        jobApi.getApiClient().setBasePath(baseServerUrl);

        baseTestUrl = properties.getProperty("io.lozzikit.mail.server-test.url");
    }

    public TemplateApi getTemplateApi() {
        return templateApi;
    }

    public MailApi getMailApi() {
        return mailApi;
    }

    public JobApi getJobApi() {
        return jobApi;
    }

    public Response executeRequest(Request request) throws IOException {
        return client.newCall(request).execute();
    }

    public String getServerUrl(String path) {
        return baseServerUrl + path;
    }

    public String getTestUrl(String path) {
        return baseTestUrl + path;
    }

    public ApiResponse getApiResponse() {
        return apiResponse;
    }

    public void setApiResponse(ApiResponse apiResponse) {
        this.apiResponse = apiResponse;
        this.apiException = null;
        this.apiCallThrewException = false;
    }

    public ApiException getApiException() {
        return apiException;
    }

    public void setApiException(ApiException apiException) {
        this.apiException = apiException;
        this.apiResponse = null;
        this.apiCallThrewException = true;
    }

    public boolean isApiCallThrewException() {
        return apiCallThrewException;
    }

    public int getApiStatusCode() {
        return (apiCallThrewException) ? apiException.getCode() : apiResponse.getStatusCode();
    }

    public static MockSmtpServer getMockSmtpServer() {
        return mockSmtpServer;
    }
}
