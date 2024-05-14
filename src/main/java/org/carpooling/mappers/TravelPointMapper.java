package org.carpooling.mappers;

import org.carpooling.models.TravelPoint;
import org.springframework.stereotype.Component;

@Component
public class TravelPointMapper {

    public TravelPointMapper () {

    }

    public TravelPoint toObj(String locationCharSequence) {
        TravelPoint travelPoint = new TravelPoint();
        travelPoint.setAddress(locationCharSequence);
        return travelPoint;
    }
}
