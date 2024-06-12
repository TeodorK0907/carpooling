package org.carpooling.helpers.errors;

public enum MailJetClientErrors {

    FAILED_RESPONSE("The request could not be processed.");

    private final String description;

    MailJetClientErrors(String description) {
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