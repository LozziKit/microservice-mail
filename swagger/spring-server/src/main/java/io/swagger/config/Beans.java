package io.swagger.config;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.Version;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Beans {
    @Bean
    public Configuration getConfiguration() {
        Version version = Configuration.VERSION_2_3_23;
        Configuration configuration = new Configuration(version);
        configuration.setObjectWrapper(new DefaultObjectWrapperBuilder(version).build());

        return configuration;
    }
}
