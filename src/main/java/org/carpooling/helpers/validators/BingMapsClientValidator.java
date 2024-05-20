package org.carpooling.helpers.validators;

import org.carpooling.helpers.constants.bing_maps_client.BingMapsClientStatus;
import org.json.JSONObject;

import static org.carpooling.helpers.constants.bing_maps_client.BingMapsClientKey.STATUS_CODE;

public class BingMapsClientValidator {
    public static boolean isResponseSuccess(JSONObject response) {
        return response.getInt(STATUS_CODE.toString()) == BingMapsClientStatus.STATUS_CODE_KEY.getCode();
    }
}
