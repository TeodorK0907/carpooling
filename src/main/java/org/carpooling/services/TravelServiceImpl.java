package org.carpooling.services;

import org.carpooling.clients.BingMapsClient;
import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.exceptions.UnsuccessfulResponseException;
import org.carpooling.helpers.constants.TravelFiltersDefaultParam;
import org.carpooling.helpers.constants.TravelStatus;
import org.carpooling.helpers.constants.bing_maps_client.BingMapsClientKey;
import org.carpooling.helpers.errors.BingMapsClientErrors;
import org.carpooling.helpers.model_filters.TravelFilterOptions;
import org.carpooling.helpers.validators.BingMapsClientValidator;
import org.carpooling.helpers.validators.TravelFilterValidator;
import org.carpooling.helpers.validators.TravelValidator;
import org.carpooling.helpers.validators.UserValidator;
import org.carpooling.models.*;
import org.carpooling.repositories.TravelRepository;
import org.carpooling.services.contracts.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public Page<Travel> getAll(User authenticatedUser, TravelFilterOptions filter) {
        Page<Travel> travels;
        Sort sort = Sort.by(
                TravelFilterValidator.getOrder(filter.getOrderBy()),
                TravelFilterValidator.getSort(filter.getSortBy()));
        Pageable page = PageRequest.of(
                TravelFilterValidator.getPageNum(filter.getPageNum())
                , TravelFilterValidator.getPageSize(filter.getPageSize()),
                sort);
        travels = travelRepository.findAllWithFilter(
                String.format(TravelFiltersDefaultParam.FORMAT, filter.getStartLocation()
                        .orElse(TravelFiltersDefaultParam.EMPTY_STRING_FILTER)),
                String.format(TravelFiltersDefaultParam.FORMAT, filter.getEndLocation()
                        .orElse(TravelFiltersDefaultParam.EMPTY_STRING_FILTER)),
                String.format(TravelFiltersDefaultParam.FORMAT, filter.getDriver()
                        .orElse(TravelFiltersDefaultParam.EMPTY_STRING_FILTER)),
                filter.getDepartAfter()
                        .orElse(TravelFiltersDefaultParam.EMPTY_DATE_AFTER),
                filter.getDepartBefore()
                        .orElse(TravelFiltersDefaultParam.EMPTY_DATE_BEFORE),
                filter.getFreeSpots()
                        .orElse(TravelFiltersDefaultParam.EMPTY_FREE_SPOTS),
                page
        );
        TravelValidator.isTravelListEmpty(travels);
        return travels;
    }

    @Override
    public Travel getById(int travelId) {
        Travel travel = travelRepository.findById(travelId).orElseThrow(
                () -> new EntityNotFoundException(TRAVEL.toString(), ID.toString(), String.valueOf(travelId)));
        TravelValidator.isTravelDeleted(travel);
        return travel;
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
        throw new UnsuccessfulResponseException(BingMapsClientErrors.FAILED_RESPONSE.toString());
    }

    @Override
    public void update(Travel travel) {
        travelRepository.save(travel);
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
    public void delete(User authenticatedUser, int travelId) {
        Travel travel = getById(travelId);
        TravelValidator.isUserCreatorOfTravel(authenticatedUser, travel.getCreator());
        travel.setArchived(true);
        travelRepository.save(travel);
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