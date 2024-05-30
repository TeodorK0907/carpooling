package org.carpooling;

import org.carpooling.config.bing_maps.BingMapsPropertiesConfig;
import org.carpooling.config.mail_jet.MailJetPropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(
        {BingMapsPropertiesConfig.class, MailJetPropertiesConfig.class})
public class CarpoolingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarpoolingApplication.class, args);
    }

}
