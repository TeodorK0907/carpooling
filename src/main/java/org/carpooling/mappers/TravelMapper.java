package org.carpooling.mappers;

import org.carpooling.helpers.constants.TravelStatus;
import org.carpooling.models.Travel;
import org.carpooling.models.User;
import org.carpooling.models.input_dto.TravelDto;
import org.carpooling.services.contracts.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TravelMapper {
    private final TravelService travelService;
    private final DateTimeMapper dateTimeMapper;
    private final TravelPointMapper travelPointMapper;

    @Autowired
    public TravelMapper (TravelService travelService,
                         DateTimeMapper dateTimeMapper,
                         TravelPointMapper travelPointMapper) {
        this.travelService = travelService;
        this.dateTimeMapper = dateTimeMapper;
        this.travelPointMapper = travelPointMapper;
    }

    public Travel toObj(User creator, TravelDto dto) {
        Travel travel = new Travel();
        travel.setDepartureTime(dateTimeMapper
                .toLocalDateTime(dto.getDepartureTime()));
        travel.setStartingPoint
                (travelPointMapper.toObj(dto.getStartingPoint()));
        travel.setEndingPoint
                (travelPointMapper.toObj(dto.getEndPoint()));
        travel.setFree_spots(dto.getFreeSpots());
        travel.setStatus(TravelStatus.PLANNED);
        travel.setCreator(creator);
        return travel;
    }


}
