package org.carpooling.clients;

import org.carpooling.config.BingMapsWebClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BingMapsClientImpl implements BingMapsClient {
    @Value("${bing.maps.api-key}")
    private String key;
    private final BingMapsWebClientConfig client;

    @Autowired
    public BingMapsClientImpl(BingMapsWebClientConfig client) {
        this.client = client;
    }

    @Override
    public String getLocationResponse(StringBuilder location) {
        return client.create().get()
                .uri(uriBuilder -> uriBuilder
                        .path(location.toString())
                        .queryParam("key", key)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
