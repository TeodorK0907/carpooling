package org.carpooling.helpers.handlers;

import org.carpooling.exceptions.UnsuccessfulResponseException;
import org.carpooling.helpers.constants.img_bb_client.ImgBBClientKey;
import org.carpooling.helpers.errors.ImgBBClientErrors;
import org.json.JSONObject;

public class ImgBBResponseHandler {

    public static String handleUploadImageResponse(String response) {
        JSONObject jsonResponse = new JSONObject(response);

        if (jsonResponse.getInt(ImgBBClientKey.STATUS.toString()) == 200) {
            JSONObject data = jsonResponse.getJSONObject(ImgBBClientKey.DATA.toString());
            return data.getString(ImgBBClientKey.DISPLAY_URL.toString());
        }
        throw new UnsuccessfulResponseException(ImgBBClientErrors.FAILED_RESPONSE.toString());
    }
}
