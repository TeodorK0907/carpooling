package org.carpooling.helpers.constants.mail_jet_client;

public enum MailJetClientKey {
    MESSAGES("Messages"),
    FROM("From"),
    EMAIL("Email"),
    NAME("Name"),
    TO("To"),
    SUBJECT("Subject"),
    TEXT_PART("TextPart"),
    HTML_PART("HTMLPart"),
    STATUS("Status"),
    MESSAGE_ID("MessageID"),
    DATA("Data"),
    ATTEMPT_COUNT("AttemptCount"),
    ID("ID");

    private final String description;

    MailJetClientKey(String description) {
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
