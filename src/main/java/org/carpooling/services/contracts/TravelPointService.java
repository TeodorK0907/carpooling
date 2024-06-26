package org.carpooling.services.contracts;

import org.carpooling.models.TravelPoint;

public interface TravelPointService {
    TravelPoint getByAddress(String address);

    TravelPoint getCoordinates(TravelPoint point);

}
