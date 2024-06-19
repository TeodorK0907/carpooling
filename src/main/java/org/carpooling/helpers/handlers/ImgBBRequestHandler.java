package org.carpooling.helpers.handlers;

import org.apache.tomcat.util.codec.binary.Base64;
import org.carpooling.helpers.constants.img_bb_client.ImgBBClientKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedMultiValueMap;

import java.nio.charset.StandardCharsets;

public class ImgBBRequestHandler {

    @Value("${img.bb.api-key}")
    private static String key;

    public static LinkedMultiValueMap<String, String> hanndleRequestBody(byte[] imgBytes) {
        LinkedMultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        String encodedImg = new String(Base64.encodeBase64String(imgBytes).getBytes(), StandardCharsets.UTF_8);
        formData.add(ImgBBClientKey.KEY.toString(), key);
        formData.add(ImgBBClientKey.IMAGE.toString(), encodedImg);
        return formData;
    }
}
