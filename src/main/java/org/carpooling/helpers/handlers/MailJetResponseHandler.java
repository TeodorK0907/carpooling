package org.carpooling.helpers.handlers;

import org.carpooling.exceptions.UnsuccessfulResponseException;
import org.carpooling.helpers.errors.MailJetClientErrors;
import org.json.JSONArray;
import org.json.JSONObject;

public class MailJetResponseHandler {

    public static String extractMailId(String response) {
        JSONObject responseJson = new JSONObject(response);
        JSONArray messages = responseJson.getJSONArray("Messages");
        if (messages.getString(0).equals("Success")) {
            JSONArray to = messages.getJSONArray(1);
            JSONObject messageId = to.getJSONObject(1);
            return messageId.getString("MessageId");
        }
        throw new UnsuccessfulResponseException(MailJetClientErrors.FAILED_RESPONSE.toString());
    }

    public static String extractStatus(String response) {
        JSONObject responseJson = new JSONObject(response);
        JSONArray dataArr = responseJson.getJSONArray("Data");
        return dataArr.getString(9);
    }
}