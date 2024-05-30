package org.carpooling.config.mail_jet;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("mail.jet")
public record MailJetPropertiesConfig (String apiUrl, String apiKey, String secretKey){
}
