package io.lozzikit.mail.api.spec.helpers;

import io.lozzikit.mail.api.TemplateApi;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Olivier Liechti on 24/06/17.
 */
public class Environment {

    private TemplateApi api = new TemplateApi();

    public Environment() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));
        String url = properties.getProperty("io.lozzikit.mail.server.url");
        api.getApiClient().setBasePath(url);

    }

    public TemplateApi getApi() {
        return api;
    }


}
