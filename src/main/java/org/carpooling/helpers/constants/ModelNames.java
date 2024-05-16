package org.carpooling.helpers.constants;

public enum ModelNames {
    USER("user"),
    TRAVEL("travel"),
    TRAVEL_POINT("travel point"),
    CANDIDATE("candidate"),
    PASSENGER("passenger"),
    RATING("rating");

    private final String description;

    ModelNames(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
