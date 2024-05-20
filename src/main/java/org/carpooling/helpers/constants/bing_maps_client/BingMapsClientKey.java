package org.carpooling.helpers.constants.bing_maps_client;

public enum BingMapsClientKey {
    ORIGINS("origins"),
    DESTINATIONS("destinations"),
    TRAVEL_MODE("travelMode"),
    KEY("key"),
    STATUS_CODE("statusCode"),
    RESOURCE_SET("resourceSets"),
    RESOURCES("resources"),
    GEOCODE_POINTS("geocodePoints"),
    USAGE_TYPES("usageTypes"),
    RESULTS("results"),
    TRAVEL_DISTANCE("travelDistance"),
    TRAVEL_DURATION("travelDuration");

    private final String description;

    BingMapsClientKey(String description) {
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
