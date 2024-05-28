package org.carpooling.helpers.errors;

public enum UserValidatorErrors {
    UNAUTHORIZED("You are unauthorized to perform the required action."),
    NO_USERS_FOUND("No users were found.");

    private final String description;

    UserValidatorErrors(String description) {
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
