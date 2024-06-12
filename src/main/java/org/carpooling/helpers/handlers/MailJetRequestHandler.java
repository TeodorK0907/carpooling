package org.carpooling.helpers.handlers;

import org.carpooling.helpers.constants.mail_jet_client.MailJetClientKey;
import org.json.JSONArray;
import org.json.JSONObject;

public class MailJetRequestHandler {
    //todo set default verification message constants
    public static String handleRequestBody(String senderEmail, String recipientEmail) {
        return new JSONObject()
                .put(MailJetClientKey.MESSAGES.toString(), new JSONArray()
                        .put(new JSONObject()
                                .put(MailJetClientKey.FROM.toString(), new JSONObject()
                                        .put(MailJetClientKey.EMAIL.toString(), senderEmail)
                                        .put(MailJetClientKey.NAME.toString(), "RoseValleyCS"))
                                .put(MailJetClientKey.TO.toString(), new JSONArray()
                                        .put(new JSONObject()
                                                .put(MailJetClientKey.EMAIL.toString(), recipientEmail)
                                                .put(MailJetClientKey.NAME.toString(), "")))
                                .put(MailJetClientKey.SUBJECT.toString(), "")
                                .put(MailJetClientKey.TEXT_PART.toString(), "")
                                .put(MailJetClientKey.HTML_PART.toString(), "<h3>mail sent successfully!</h3><br />.")
                        )).toString();
    }
}
