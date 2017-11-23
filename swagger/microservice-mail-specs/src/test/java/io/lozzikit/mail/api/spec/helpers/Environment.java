package io.lozzikit.mail.api.spec.helpers;

import io.lozzikit.mail.ApiException;
import io.lozzikit.mail.ApiResponse;
import io.lozzikit.mail.api.JobApi;
import io.lozzikit.mail.api.MailApi;
import io.lozzikit.mail.api.TemplateApi;

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

    // Response
    private ApiResponse apiResponse;
    private ApiException apiException;
    private boolean apiCallThrewException;

    public Environment() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));
        String url = properties.getProperty("io.lozzikit.mail.server.url");
        templateApi.getApiClient().setBasePath(url);
        mailApi.getApiClient().setBasePath(url);
        jobApi.getApiClient().setBasePath(url);

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
}
