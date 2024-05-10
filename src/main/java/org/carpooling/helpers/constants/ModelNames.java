package org.carpooling.helpers.constants;

public enum ModelNames {
    USER("user"),
    TRAVEL("travel"),
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
