package org.carpooling.helpers.constants.attribute_constants;

public enum TravelPointAttributes {
    ID("id"),
    ADDRESS("address"),
    LATITUDE("latitute"),
    LONGITUDE("longitude");

    private final String description;

    TravelPointAttributes(String description) {
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
