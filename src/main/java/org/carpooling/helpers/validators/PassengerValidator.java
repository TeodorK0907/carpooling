package org.carpooling.helpers.validators;

import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.helpers.constants.ModelNames;
import org.carpooling.helpers.constants.attribute_constants.PassengerAttributes;
import org.carpooling.helpers.constants.attribute_constants.TravelAttributes;
import org.carpooling.models.Passenger;
import org.carpooling.models.Travel;
import org.carpooling.models.User;

public class PassengerValidator {
    public static boolean isPassengerInTravel(Travel travel, Passenger passenger) {
        if (!travel.getPassengers().contains(passenger)) {
            throw new EntityNotFoundException(
                    ModelNames.PASSENGER.toString(),
                    PassengerAttributes.USER_ID.toString(),
                    String.valueOf(passenger.getUserId()),
                    ModelNames.TRAVEL.toString(),
                    TravelAttributes.ID.toString(),
                    String.valueOf(travel.getId())
            );
        }
        return true;
    }

    public static boolean isUserPassenger(User user, Passenger passenger) {
        return user.getId() == passenger.getUserId();
    }
}