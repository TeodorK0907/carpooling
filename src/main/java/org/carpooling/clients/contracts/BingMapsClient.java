package org.carpooling.clients.contracts;


public interface BingMapsClient {
    String getLocationResponse(StringBuilder location);

    String getDistanceMatrixResponse(StringBuilder origins, StringBuilder destinations);

}
