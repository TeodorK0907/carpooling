package org.carpooling.services;

import org.carpooling.clients.BingMapsClient;
import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.exceptions.UnsuccessfulResponseException;
import org.carpooling.helpers.constants.TravelFilters;
import org.carpooling.helpers.constants.TravelStatus;
import org.carpooling.helpers.constants.bing_maps_client.BingMapsClientKey;
import org.carpooling.helpers.model_filters.TravelFilterOptions;
import org.carpooling.helpers.validators.BingMapsClientValidator;
import org.carpooling.helpers.validators.TravelValidator;
import org.carpooling.helpers.validators.UserValidator;
import org.carpooling.models.Travel;
import org.carpooling.models.TravelPoint;
import org.carpooling.models.User;
import org.carpooling.repositories.TravelRepository;
import org.carpooling.services.contracts.CommentService;
import org.carpooling.services.contracts.TravelPointService;
import org.carpooling.services.contracts.TravelService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.carpooling.helpers.constants.ModelNames.TRAVEL;
import static org.carpooling.helpers.constants.attribute_constants.UserAttribute.ID;

@Service
public class TravelServiceImpl implements TravelService {
    private static final String SEPARATOR = ",";
    private static final int FIRST_INDEX = 0;
    private final TravelRepository travelRepository;
    private final TravelPointService pointService;
    private final CommentService commentService;
    private final BingMapsClient client;

    @Autowired
    public TravelServiceImpl(TravelRepository travelRepository,
                             TravelPointService pointService,
                             CommentService commentService,
                             BingMapsClient client) {
        this.travelRepository = travelRepository;
        this.pointService = pointService;
        this.commentService = commentService;
        this.client = client;
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

    @Override
    public Travel create(User creator, Travel travel, String commentContent) {
        UserValidator.isBlocked(creator);
        if (TravelValidator.isLatLongEmpty(travel.getStartingPoint())) {
            pointService.getCoordinates(travel.getStartingPoint());
        }
        if (TravelValidator.isLatLongEmpty(travel.getEndingPoint())) {
            pointService.getCoordinates(travel.getEndingPoint());
        }
        StringBuilder origins = buildLatLongParams(travel.getStartingPoint());
        StringBuilder destinations = buildLatLongParams(travel.getEndingPoint());
        JSONObject distanceResponse = new JSONObject(
                client.getDistanceMatrixResponse(origins, destinations)
        );
        if (BingMapsClientValidator.isResponseSuccess(distanceResponse)) {
            populateDurationDistance(distanceResponse, travel);
            travelRepository.save(travel);
            commentService.create(commentContent, travel.getId());
            return travel;
        }
        throw new UnsuccessfulResponseException("The request could not be processed.");
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

    //todo create candidate service and repo
    @Override
    public Travel apply(User authenticatedUser, int travelId) {
        UserValidator.isBlocked(authenticatedUser);

        Travel travelToApply = getById(travelId);
        //travelToApply.getCandidates().add()
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

    private StringBuilder buildLatLongParams(TravelPoint point) {
        return new StringBuilder()
                .append(point.getLatitude())
                .append(SEPARATOR)
                .append(point.getLongitude());
    }

    private void populateDurationDistance(JSONObject distanceResponse, Travel travel) {
        JSONArray resourceSets = distanceResponse.getJSONArray(BingMapsClientKey.RESOURCE_SET.toString());
        JSONObject resourcesSetMap = resourceSets.getJSONObject(FIRST_INDEX);
        JSONArray resources = resourcesSetMap.getJSONArray(BingMapsClientKey.RESOURCES.toString());
        JSONObject resourcesMap = resources.getJSONObject(FIRST_INDEX);
        JSONArray results = resourcesMap.getJSONArray(BingMapsClientKey.RESULTS.toString());
        JSONObject resultsMap = results.getJSONObject(FIRST_INDEX);
        travel.setDistance(resultsMap.getDouble(BingMapsClientKey.TRAVEL_DISTANCE.toString()));
        travel.setDuration(resultsMap.getDouble(BingMapsClientKey.TRAVEL_DURATION.toString()));
    }
}
