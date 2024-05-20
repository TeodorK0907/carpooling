package org.carpooling.mappers;

import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.models.TravelPoint;
import org.carpooling.services.contracts.TravelPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TravelPointMapper {
    private final TravelPointService pointService;
    @Autowired
    public TravelPointMapper (TravelPointService pointService) {
        this.pointService = pointService;
    }

    public TravelPoint toObj(String locationCharSequence) {
        try {
            return pointService.getByAddress(locationCharSequence);
        } catch (EntityNotFoundException e) {
            TravelPoint travelPoint = new TravelPoint();
            travelPoint.setAddress(locationCharSequence);
            return travelPoint;
        }
    }
}
