package org.carpooling.mappers;

import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.helpers.constants.TravelStatus;
import org.carpooling.models.Travel;
import org.carpooling.models.User;
import org.carpooling.models.input_dto.TravelDto;
import org.carpooling.models.output_dto.TravelOutputDto;
import org.carpooling.services.contracts.CommentService;
import org.carpooling.services.contracts.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TravelMapper {
    private final TravelService travelService;
    private final CommentService commentService;
    private final DateTimeMapper dateTimeMapper;
    private final TravelPointMapper travelPointMapper;

    @Autowired
    public TravelMapper (TravelService travelService,
                         CommentService commentService,
                         DateTimeMapper dateTimeMapper,
                         TravelPointMapper travelPointMapper) {
        this.travelService = travelService;
        this.commentService = commentService;
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

    public TravelOutputDto toDto(Travel travel) {
        TravelOutputDto output = new TravelOutputDto();
        output.setTravelId(travel.getId());
        output.setDriverName(travel.getCreator().getUsername());
        output.setStartingPoint(travel.getStartingPoint().getAddress());
        output.setEndingPoint(travel.getEndingPoint().getAddress());
        output.setDepartureTime(travel.getDepartureTime());
        output.setFreeSpots(travel.getFree_spots());
        output.setStatus(travel.getStatus().toString());
        output.setDuration(travel.getDuration());
        output.setDistance(travel.getDistance());
        try {
            output.setComment(commentService.getByTravelId(travel.getId()).getContent());
        } catch (EntityNotFoundException e) {
            output.setComment(null);
        }
        output.getCandidates().addAll(travel.getCandidates());
        output.getPassengers().addAll(travel.getPassengers());
        return output;
    }

}