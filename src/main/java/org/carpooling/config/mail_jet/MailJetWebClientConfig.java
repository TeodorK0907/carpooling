package org.carpooling.config.mail_jet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@Configuration
public class MailJetWebClientConfig {
    @Value("${mail.jet.api-url}")
    private String mailJetUrl;
    @Value("${mail.jet.api-key}")
    private String username;
    @Value("${mail.jet.secret-key}")
    private String password;

    @Bean
    public WebClient getWithMailJetBaseUrl() {
        return WebClient.builder()
                .baseUrl(mailJetUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filter(basicAuthentication(username, password))
                .exchangeStrategies(ExchangeStrategies.builder().codecs(c ->
                        c.defaultCodecs().enableLoggingRequestDetails(true)).build())
                .build();
    }
}
