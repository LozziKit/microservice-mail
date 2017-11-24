package io.lozzikit.mail.config;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.Version;
import io.lozzikit.mail.smtp.SmtpServerDescription;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Beans {
    @Value("${io.lozzikit.smtp.debug}")
    private Boolean smtpDebug;

    @Value("${io.lozzikit.smtp.address}")
    private String smtpAddress;

    @Value("${io.lozzikit.smtp.port}")
    private Integer smtpPort;

    @Bean
    public Configuration getConfiguration() {
        Version version = Configuration.VERSION_2_3_23;
        Configuration configuration = new Configuration(version);
        configuration.setObjectWrapper(new DefaultObjectWrapperBuilder(version).build());

        return configuration;
    }

    @Bean
    public SmtpServerDescription getSmtpServerDescription() {
        if (smtpDebug) {
            return new SmtpServerDescription() {
                @Override
                public String getAddress() {
                    return "localhost";
                }

                @Override
                public Integer getPort() {
                    return 2525;
                }
            };
        } else {
            return new SmtpServerDescription() {
                @Override
                public String getAddress() {
                    return smtpAddress;
                }

                @Override
                public Integer getPort() {
                    return smtpPort;
                }
            };
        }
    }
}
