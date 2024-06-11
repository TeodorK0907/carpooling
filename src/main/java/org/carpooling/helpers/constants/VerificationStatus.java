package org.carpooling.helpers.constants;

public enum VerificationStatus {
    SENT("sent"),
    OPENED("opened"),
    CLICKED("clicked");

    private final String description;

    VerificationStatus(String description) {
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
