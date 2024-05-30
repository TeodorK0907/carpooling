package org.carpooling.config.bing_maps;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("bing.maps")
public record BingMapsPropertiesConfig (String apiUrl, String apiKey){
}
