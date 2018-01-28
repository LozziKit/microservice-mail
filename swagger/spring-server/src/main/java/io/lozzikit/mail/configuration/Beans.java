package io.lozzikit.mail.configuration;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.Version;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Beans for swagger
 */
@org.springframework.context.annotation.Configuration
public class Beans {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * Getter for configuration of swagger/spring
     * @return
     */
    @Bean
    public Configuration getConfiguration() {
        Version version = Configuration.VERSION_2_3_23;
        Configuration configuration = new Configuration(version);
        configuration.setObjectWrapper(new DefaultObjectWrapperBuilder(version).build());

        return configuration;
    }
}
