package org.carpooling.helpers.errors;

public enum AuthenticationErrors {
    FAILED_AUTH("The requested resource requires authentication."),

    AUTH_DETAILS_MISMATCH("Incorrect username or password.");

    private final String description;

    AuthenticationErrors(String description) {
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
