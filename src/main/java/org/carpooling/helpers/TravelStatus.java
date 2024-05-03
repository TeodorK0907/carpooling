package org.carpooling.helpers;

public enum TravelStatus {
    PLANNED("Planned"),
    ACTIVE("Active"),
    CANCELLED("Cancelled"),
    COMPLETED("Completed");

    private final String description;

    TravelStatus(String description) {
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
