package org.carpooling.helpers.validators;

import org.carpooling.exceptions.BadRequestException;
import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.exceptions.UnauthorizedOperationException;
import org.carpooling.helpers.constants.TravelStatus;
import org.carpooling.models.Travel;
import org.carpooling.models.User;

import java.util.List;

public class TravelValidator {

    //todo remove below magic String
    public static boolean isTravelListEmpty(List<Travel> list) {
        if (list.isEmpty()) {
            throw new EntityNotFoundException("No planned travels were found.");
        }
        return false;
    }


    //todo remove below magic String
    public static boolean isStatusActive(Travel travel) {
        if (travel.getStatus() != TravelStatus.ACTIVE) {
            throw new BadRequestException("Only a travel with an active status could be marked as completed.");
        }
        return true;
    }

    public static boolean isUserCreatorOfTravel(User authenticatedUser, User creator) {
        if (!authenticatedUser.equals(creator)) {
            throw new UnauthorizedOperationException("You are unauthorized to perform the required action.");
        }
        return true;
    }

    public static boolean isStatusCancelable(Travel toBeMarkedCancelled) {
        switch (toBeMarkedCancelled.getStatus()) {
            case ACTIVE, PLANNED:
                return true;
            default:
                throw new BadRequestException("Only an active or planned travel could be cancelled.");
        }
    }
}
