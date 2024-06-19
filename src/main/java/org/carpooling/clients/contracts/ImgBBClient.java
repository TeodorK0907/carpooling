package org.carpooling.clients.contracts;

import org.springframework.util.LinkedMultiValueMap;

public interface ImgBBClient {
    String uploadImage(LinkedMultiValueMap<String, String> formData);
}
