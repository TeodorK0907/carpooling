package org.carpooling.services;

import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.helpers.constants.TravelFilters;
import org.carpooling.helpers.constants.TravelStatus;
import org.carpooling.helpers.model_filters.TravelFilterOptions;
import org.carpooling.helpers.validators.TravelValidator;
import org.carpooling.helpers.validators.UserValidator;
import org.carpooling.models.Travel;
import org.carpooling.models.User;
import org.carpooling.repositories.TravelRepository;
import org.carpooling.services.contracts.TravelPointService;
import org.carpooling.services.contracts.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.carpooling.helpers.constants.ModelNames.TRAVEL;
import static org.carpooling.helpers.constants.attribute_constants.UserAttribute.ID;

@Service
public class TravelServiceImpl implements TravelService {

    private final TravelRepository travelRepository;
    private final TravelPointService pointService;
    @Autowired
    public TravelServiceImpl(TravelRepository travelRepository,
                             TravelPointService pointService) {
        this.travelRepository = travelRepository;
        this.pointService = pointService;
    }

    //todo test getAll once travels can be created
    @Override
    public Page<Travel> getAll(User authenticatedUser, TravelFilterOptions filter) {
        Page<Travel> travels;
        Pageable page = PageRequest.of(0, 10);
        travels = travelRepository.findAllWithFilter(
                String.format("%%%s%%", filter.getStartLocation()
                        .orElse(TravelFilters.EMPTY_STRING_FILTER)),
                String.format("%%%s%%", filter.getEndLocation()
                        .orElse(TravelFilters.EMPTY_STRING_FILTER)),
                String.format("%%%s%%", filter.getDriver()
                        .orElse(TravelFilters.EMPTY_STRING_FILTER)),
                filter.getDepartAfter()
                        .orElse(TravelFilters.EMPTY_DATE_AFTER),
                filter.getDepartBefore()
                        .orElse(TravelFilters.EMPTY_DATE_BEFORE),
                filter.getFreeSpots()
                        .orElse(TravelFilters.EMPTY_FREE_SPOTS),
                page
        );
        TravelValidator.isTravelListEmpty(travels);
        return travels;
    }

    @Override
    public Travel getById(int travelId) {
        return travelRepository.findById(travelId).orElseThrow(
                () -> new EntityNotFoundException(TRAVEL.toString(), ID.toString(), String.valueOf(travelId)));
    }

    //todo configure webClient, client layer for both requests and responses
    @Override
    public Travel create(User creator, Travel travel) {
        UserValidator.isBlocked(creator);
        //todo to move to client location method

        //todo to move to client duration method
//StringBuilder duration = new StringBuilder("/Routes/DistanceMatrix");
        //todo perform validation and determine whether a request for location is necessary
        if (TravelValidator.isLatLongEmpty(travel.getStartingPoint())) {
            pointService.getLatLong(travel.getStartingPoint());
//            String startLocationResponse = client.getLocationResponse(
//                    travel.getStartingPoint().getAddress());
//            populateTravelPoint(startLocationResponse, travel.getStartingPoint());
//        }
//        //todo perform validation and determine whether a request for location is necessary
//        if (TravelValidator.isLatLongEmpty(travel.getEndingPoint())) {
//            populateLocation(location);
//            String endLocationResponse = client.getLocationResponse(location);
//            populateTravelPoint(endLocationResponse, travel.getStartingPoint());
//        }
//
//        String durationResponse = client.getDuration(travel.getStartingPoint(), travel.getEndingPoint());
        }
        return null;
    }

    @Override
    public Travel complete(User authenticatedUser, int travelId) {
        Travel toBeMarkedComplete = getById(travelId);
        TravelValidator.isStatusActive(toBeMarkedComplete);
        toBeMarkedComplete.setStatus(TravelStatus.COMPLETED);
        travelRepository.save(toBeMarkedComplete);
        return toBeMarkedComplete;
    }

    @Override
    public Travel cancel(User authenticatedUser, int travelId) {
        Travel toBeMarkedCancelled = getById(travelId);
        TravelValidator.isUserCreatorOfTravel
                (authenticatedUser, toBeMarkedCancelled.getCreator());
        TravelValidator.isStatusCancelable(toBeMarkedCancelled);
        toBeMarkedCancelled.setStatus(TravelStatus.CANCELLED);
        return toBeMarkedCancelled;
    }

    @Override
    public Travel apply(User authenticatedUser, int travelId) {
        return null;
    }

    @Override
    public Travel resign(User authenticatedUser, int travelId) {
        return null;
    }

    @Override
    public Travel approve(User authenticatedUser, int travelId, int candidateId) {
        return null;
    }

    @Override
    public Travel decline(User authenticatedUser, int travelId, int candidateId) {
        return null;
    }

    @Override
    public void delete(User authenticatedUser, int travelId) {

    }
}
