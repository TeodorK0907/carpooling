package org.carpooling.clients;

import org.carpooling.config.BingMapsWebClientConfig;
import org.carpooling.helpers.constants.bing_maps_client.BingMapsClientEndpoint;
import org.carpooling.helpers.constants.bing_maps_client.BingMapsClientKey;
import org.carpooling.helpers.constants.bing_maps_client.BingMapsClientValue;
import org.carpooling.models.TravelPoint;
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
        return client.create()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(location.toString())
                        .queryParam(BingMapsClientKey.KEY.toString(), key)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public String getDistanceMatrixResponse(StringBuilder origins, StringBuilder destinations) {
        return client.create()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(BingMapsClientEndpoint.DISTANCE_MATRIX.toString())
                        .queryParam(BingMapsClientKey.ORIGINS.toString(), origins.toString())
                        .queryParam(BingMapsClientKey.DESTINATIONS.toString(), destinations.toString())
                        .queryParam(BingMapsClientKey.TRAVEL_MODE.toString(), BingMapsClientValue.DRIVING.toString())
                        .queryParam(BingMapsClientKey.KEY.toString(), key)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}