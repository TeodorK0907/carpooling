package org.carpooling.helpers.errors;

public enum TravelValidatorErrors {

    NO_TRAVELS_FOUND("No planned travels were found."),
    STATUS_NOT_ACTIVE("The provided travel's status is not active."),
    STATUS_NOT_COMPLETED("The provided travel's status is not completed."),
    STATUS_NOT_CANCELABLE("Only an active or planned travel could be cancelled."),
    UNAUTHORIZED("You are unauthorized to perform the required action.");

    private final String description;

    TravelValidatorErrors(String description) {
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
