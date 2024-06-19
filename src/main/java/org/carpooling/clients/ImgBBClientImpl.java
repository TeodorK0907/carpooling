package org.carpooling.clients;

import org.carpooling.clients.contracts.ImgBBClient;
import org.carpooling.config.img_bb.ImgBBWebClientConfig;
import org.carpooling.helpers.handlers.ImgBBResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;

@Component
public class ImgBBClientImpl implements ImgBBClient {

    private final ImgBBWebClientConfig client;

    @Autowired
    public ImgBBClientImpl(ImgBBWebClientConfig client) {
        this.client = client;
    }

    @Override
    public String uploadImage(LinkedMultiValueMap<String, String> formData) {

        return ImgBBResponseHandler.handleUploadImageResponse(
                client.getWithImgBBUrlAndEndpoint()
                .post()
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(String.class)
                .block()
        );
    }
}
