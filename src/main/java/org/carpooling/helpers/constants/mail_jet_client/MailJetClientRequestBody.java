package org.carpooling.helpers.constants.mail_jet_client;

public enum MailJetClientRequestBody {
    BLANK(""),
    EMAIL_HTML_PART("<h3>mail sent successfully!, please click to validate " +
            "<a href=\"https://www.google.com/\">RoseValleyTravel</a>!</h3><br />.");

    private final String description;

    MailJetClientRequestBody(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return description;
    }
}
