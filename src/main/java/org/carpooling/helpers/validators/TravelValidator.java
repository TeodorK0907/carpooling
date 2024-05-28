package org.carpooling.helpers.validators;

import org.carpooling.exceptions.BadRequestException;
import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.exceptions.UnauthorizedOperationException;
import org.carpooling.helpers.constants.TravelStatus;
import org.carpooling.models.Travel;
import org.carpooling.models.TravelPoint;
import org.carpooling.models.User;
import org.springframework.data.domain.Page;

import static org.carpooling.helpers.constants.ModelNames.TRAVEL;
import static org.carpooling.helpers.constants.attribute_constants.UserAttribute.ID;


public class TravelValidator {

    //todo remove below magic String
    public static boolean isTravelListEmpty(Page<Travel> list) {
        if (list.getContent().isEmpty()) {
            throw new EntityNotFoundException("No planned travels were found.");
        }
        return false;
    }


    //todo remove below magic String
    public static boolean isStatusActive(Travel travel) {
        if (travel.getStatus() != TravelStatus.ACTIVE) {
            throw new BadRequestException("Only a travel with status Active could be marked as completed.");
        }
        return true;
    }
    //todo remove below magic string
    public static boolean isUserCreatorOfTravel(User authenticatedUser, User creator) {
        if (!authenticatedUser.equals(creator)) {
            throw new UnauthorizedOperationException("You are unauthorized to perform the required action.");
        }
        return true;
    }

    public static boolean isCreatorOfTravelCurrentUser(User authenticatedUser, User creator) {
        return authenticatedUser.equals(creator);
    }
    //todo remove below magic string
    public static boolean isStatusCancelable(Travel toBeMarkedCancelled) {
        switch (toBeMarkedCancelled.getStatus()) {
            case ACTIVE, PLANNED:
                return true;
            default:
                throw new BadRequestException("Only an active or planned travel could be cancelled.");
        }
    }

    public static boolean isLatLongEmpty(TravelPoint startingPoint) {
        return startingPoint.getLatitude() == null && startingPoint.getLongitude() == null;
    }
    //todo remove below magic string
    public static void isStatusCompleted(Travel travel) {
        if (travel.getStatus() != TravelStatus.COMPLETED) {
            throw new BadRequestException(String.format("Travel status is %s, status must be completed",
                    travel.getStatus().toString()));
        }
    }

    public static boolean isTravelDeleted(Travel travel) {
        if (travel.isArchived()) {
            throw new EntityNotFoundException(TRAVEL.toString(), ID.toString(), String.valueOf(travel.getId()));
        }
        return false;
    }
}