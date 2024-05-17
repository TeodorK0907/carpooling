package org.carpooling.helpers.constants.bing_maps_client;
public enum BingMapsClientEndpoint {
    LOCATION("/Locations/BG/"),
    DISTANCE_MATRIX("/Routes/DistanceMatrix");

    private final String description;

    BingMapsClientEndpoint(String description) {
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
