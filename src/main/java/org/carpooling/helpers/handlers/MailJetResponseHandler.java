package org.carpooling.helpers.handlers;

import org.carpooling.exceptions.UnsuccessfulResponseException;
import org.carpooling.helpers.constants.mail_jet_client.MailJetClientKey;
import org.carpooling.helpers.errors.MailJetClientErrors;
import org.json.JSONArray;
import org.json.JSONObject;

public class MailJetResponseHandler {
    private static final int FIRST_INDEX = 0;
    public static Long extractMailId(String response) {
        JSONObject responseJson = new JSONObject(response);
        JSONArray messages = responseJson.getJSONArray(MailJetClientKey.MESSAGES.toString());
        JSONObject messagesContent = messages.getJSONObject(FIRST_INDEX);
        if (messagesContent.getString(
                MailJetClientKey.STATUS.toString()).equals(MailJetClientKey.SUCCESS.toString()))
        {
            JSONArray to = messagesContent.getJSONArray(MailJetClientKey.TO.toString());
            JSONObject toContent = to.getJSONObject(FIRST_INDEX);
            return toContent.getLong(MailJetClientKey.MESSAGE_ID.toString());

        }
        throw new UnsuccessfulResponseException(MailJetClientErrors.FAILED_RESPONSE.toString());
    }

    public static String extractStatus(String response) {
        JSONObject responseJson = new JSONObject(response);
        JSONArray dataArr = responseJson.getJSONArray(MailJetClientKey.DATA.toString());
        JSONObject data = dataArr.getJSONObject(FIRST_INDEX);
        return data.getString(MailJetClientKey.STATUS.toString());
    }
}