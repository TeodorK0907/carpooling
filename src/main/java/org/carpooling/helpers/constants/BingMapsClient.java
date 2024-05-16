package org.carpooling.helpers.constants;
//todo separate key, endpoints, values into separate enums
public enum BingMapsClient {
    LOCATION_ENDPOINT("/Locations/BG/"),
    STATUS_CODE_KEY("statusCode"),
    RESOURCE_SET_KEY("resourceSets"),
    RESOURSES_KEY("resources"),
    GEOCODE_POINTS_KEY("geocodePoints"),
    USAGE_TYPE_KEY("usageTypes"),
    ROUTE_VALUE("Route"),
    COORDINATES_VALUE("coordinates");

    private final String description;

    BingMapsClient(String description) {
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
