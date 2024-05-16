package org.carpooling.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BingMapsWebClientConfig {
    @Value("${bing.maps.api-url}")
    private String bingMapsURL;

    @Bean
    public WebClient create() {
        return WebClient.create(bingMapsURL);
    }
}
