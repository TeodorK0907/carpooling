package org.carpooling.clients;

import org.carpooling.models.TravelPoint;

public interface BingMapsClient {
    String getLocationResponse(StringBuilder location);

    String getDistanceMatrixResponse(TravelPoint origins, TravelPoint destinations);

}
