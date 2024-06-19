package org.carpooling.config.img_bb;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ImgBBWebClientConfig {
    @Value("${img.bb.api-url}")
    private String imgBBUrl;

    @Bean
    public WebClient getWithImgBBUrlAndEndpoint() {
        return WebClient.create(imgBBUrl);
    }
}
