package org.carpooling.services;

import org.carpooling.clients.BingMapsClient;
import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.exceptions.UnsuccessfulResponseException;
import org.carpooling.helpers.constants.ModelNames;
import org.carpooling.helpers.constants.attribute_constants.TravelPointAttributes;
import org.carpooling.helpers.errors.BingMapsClientErrors;
import org.carpooling.helpers.validators.BingMapsClientValidator;
import org.carpooling.models.TravelPoint;
import org.carpooling.repositories.PointRepository;
import org.carpooling.services.contracts.TravelPointService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.carpooling.helpers.constants.bing_maps_client.BingMapsClientKey.*;
import static org.carpooling.helpers.constants.bing_maps_client.BingMapsClientValue.COORDINATES;
import static org.carpooling.helpers.constants.bing_maps_client.BingMapsClientValue.ROUTE;

@Service
public class TravelPointServiceImpl implements TravelPointService {
    private static final int FIRST_INDEX = 0;
    private static final int SECOND_INDEX = 1;
    private static final int ONE = 1;
    private static final String DELIMITER = ",";
    private static final String REPLACEMENT = "/";
    private final BingMapsClient client;
    private final PointRepository pointRepository;

    @Autowired
    public TravelPointServiceImpl(BingMapsClient client,
                                  PointRepository pointRepository) {
        this.client = client;
        this.pointRepository = pointRepository;
    }

    @Override
    public TravelPoint getByAddress(String address) {
        return pointRepository.findByAddress(address)
                .orElseThrow(() -> new EntityNotFoundException(
                        ModelNames.TRAVEL_POINT.toString(),
                        TravelPointAttributes.ADDRESS.toString(), address)
                );
    }

    @Override
    public TravelPoint getCoordinates(TravelPoint point) {
        StringBuilder location = new StringBuilder("/Locations/BG/");
        populateLocation(location, point.getAddress());
        JSONObject locationResponse = new JSONObject(client.getLocationResponse(location));
        if (BingMapsClientValidator.isResponseSuccess(locationResponse)) {
            JSONArray geoCodePoints = getGeocodePoints(locationResponse);
            populatePoint(point, geoCodePoints);
            pointRepository.save(point);
            return point;
        }
        throw new UnsuccessfulResponseException(BingMapsClientErrors.FAILED_RESPONSE.toString());
    }

    private void populateLocation(StringBuilder location, String address) {
        location.append(address.replaceAll(DELIMITER, REPLACEMENT));
    }

    private JSONArray getGeocodePoints(JSONObject responseJson) {
        JSONArray resourceSets = responseJson.getJSONArray(RESOURCE_SET.toString());
        JSONObject resources = resourceSets.getJSONObject(FIRST_INDEX);
        JSONArray resourceArr = resources.getJSONArray(RESOURCES.toString());
        JSONObject resourceMap = resourceArr.getJSONObject(FIRST_INDEX);
        return resourceMap.getJSONArray(GEOCODE_POINTS.toString());
    }

    private void populatePoint(TravelPoint point, JSONArray geocodePoints) {
        if (geocodePoints.length() > ONE) {
            for (int i = FIRST_INDEX; i < geocodePoints.length(); i++) {
                JSONObject geoPoint = geocodePoints.getJSONObject(i);
                if (geoPoint
                        .getJSONArray(USAGE_TYPES.toString())
                        .getString(FIRST_INDEX).equals(ROUTE.toString())) {
                    populateLatLong(point, geoPoint);
                }
            }
        } else {
            populateLatLong(point, geocodePoints.getJSONObject(FIRST_INDEX));
        }
    }

    private void populateLatLong(TravelPoint point, JSONObject geoPoint) {
        JSONArray coordinates = geoPoint.getJSONArray(COORDINATES.toString());
        point.setLatitude(coordinates.getDouble(FIRST_INDEX));
        point.setLongitude(coordinates.getDouble(SECOND_INDEX));
    }
}