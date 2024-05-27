package org.carpooling.helpers.validators;

import org.carpooling.exceptions.DuplicateRequestException;
import org.carpooling.helpers.constants.ModelNames;
import org.carpooling.helpers.constants.attribute_constants.TravelAttributes;
import org.carpooling.helpers.constants.attribute_constants.UserAttribute;
import org.carpooling.models.Passenger;

public class RatingValidator {
    private static final String ACTION_RECEIVE = "received";
    private static final String ACTION_GIVE = "gavin";
    public static boolean isPassengerAlreadyGivenRating(Passenger passenger, int travelId) {
        if (passenger.isGivenRating()) {
            throw new DuplicateRequestException(ModelNames.USER.toString(),
                    UserAttribute.ID.toString(),
                    String.valueOf(passenger.getUserId()),
                    ACTION_RECEIVE,
                    ModelNames.RATING.toString(),
                    ModelNames.TRAVEL.toString(),
                    TravelAttributes.ID.toString(),
                    String.valueOf(travelId)
            );
        }
        return false;
    }

    public static void hasPassengerAlreadyGivenRating(Passenger passenger, int travelId) {
        if (passenger.isGaveRating()) {
            throw new DuplicateRequestException(ModelNames.USER.toString(),
                    UserAttribute.ID.toString(),
                    String.valueOf(passenger.getUserId()),
                    ACTION_GIVE,
                    ModelNames.RATING.toString(),
                    ModelNames.TRAVEL.toString(),
                    TravelAttributes.ID.toString(),
                    String.valueOf(travelId)
            );
        }
    }
}
