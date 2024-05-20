package org.carpooling.clients;


public interface BingMapsClient {
    String getLocationResponse(StringBuilder location);

    String getDistanceMatrixResponse(StringBuilder origins, StringBuilder destinations);

}
