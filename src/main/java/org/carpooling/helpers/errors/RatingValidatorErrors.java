package org.carpooling.helpers.errors;

public enum RatingValidatorErrors {
    SAME_USER("You cannot give feedback to yourself.");

    private final String description;

    RatingValidatorErrors(String description) {
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
