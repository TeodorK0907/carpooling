package org.carpooling.helpers.constants.attribute_constants;

public enum TravelAttributes {
    ID("id"),
    CREATOR("creator"),
    DEPARTURE_TIME("departureTime"),
    FREE_SPOTS("free spots"),
    STATUS("status"),
    DURATION("duration"),
    DISTANCE("distance");

    private final String description;

    TravelAttributes(String description) {
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
