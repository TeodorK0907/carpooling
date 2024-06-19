package org.carpooling.config.img_bb;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("img.bb")
public record ImgBBPropertiesConfig(String apiUrl, String apiKey) {
}
