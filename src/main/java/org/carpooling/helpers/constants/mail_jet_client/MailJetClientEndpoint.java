package org.carpooling.helpers.constants.mail_jet_client;
public enum MailJetClientEndpoint {
    SEND("/send"),
    MESSAGE("/message");

    private final String description;

    MailJetClientEndpoint(String description) {
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
