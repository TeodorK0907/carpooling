package org.carpooling.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("bing.maps")
public record BingMapsPropertiesConfig (String apiUrl, String apiKey){
}
