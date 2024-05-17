package org.carpooling.helpers.constants.bing_maps_client;

public enum BingMapsClientValue {
    DRIVING("driving"),
    ROUTE("Route"),
    COORDINATES("coordinates");

    private final String description;

    BingMapsClientValue(String description) {
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
