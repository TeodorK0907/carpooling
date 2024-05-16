package org.carpooling.services;

import org.carpooling.clients.BingMapsClient;
import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.helpers.constants.ModelNames;
import org.carpooling.models.TravelPoint;
import org.carpooling.repositories.PointRepository;
import org.carpooling.services.contracts.TravelPointService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.carpooling.helpers.constants.BingMapsClient.*;

@Service
public class TravelPointServiceImpl implements TravelPointService {
    private final BingMapsClient client;
    private final PointRepository pointRepo;

    @Autowired
    public TravelPointServiceImpl(BingMapsClient client,
                                  PointRepository pointRepo) {
        this.client = client;
        this.pointRepo = pointRepo;
    }

    @Override
    public TravelPoint getByAddress(String address) {
        return pointRepo.findByAddress(address)
                .orElseThrow(() -> new EntityNotFoundException(ModelNames.TRAVEL_POINT.toString(), "address", address));
    }
    //todo make LatLong method more readable
    @Override
    public TravelPoint getLatLong(TravelPoint point) {
        StringBuilder location = new StringBuilder("/Locations/BG/");
        populateLocation(location, point.getAddress());
        JSONObject responseJson = new JSONObject(client.getLocationResponse(location));
        if (responseJson.getInt(STATUS_CODE_KEY.toString()) == 200){
            JSONArray resourceSets = responseJson.getJSONArray(RESOURCE_SET_KEY.toString());
            JSONObject resources = resourceSets.getJSONObject(0);
            JSONArray resourceArr = resources.getJSONArray(RESOURSES_KEY.toString());
            JSONObject resourceMap = resourceArr.getJSONObject(0);
            JSONArray geocodePoints = resourceMap.getJSONArray(GEOCODE_POINTS_KEY.toString());
            populatePoint(point, geocodePoints);
        }
        return null;
    }

    private void populatePoint(TravelPoint point, JSONArray geocodePoints) {
        for (int i = 0; i < geocodePoints.length(); i++) {
            JSONObject geoPoint = geocodePoints.getJSONObject(i);
            if (geoPoint
                    .getJSONArray(USAGE_TYPE_KEY.toString())
                    .getString(0).equals(ROUTE_VALUE.toString())) {
                JSONArray coordinates = geoPoint.getJSONArray(COORDINATES_VALUE.toString());
                point.setLatitude(coordinates.getDouble(0));
                point.setLongitude(coordinates.getDouble(1));
            }
        }
    }

    private void populateLocation(StringBuilder location, String address) {
        location.append(address.replaceAll(",", "/"));
    }
}
