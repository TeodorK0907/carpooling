package org.carpooling.helpers.validators;

import org.carpooling.exceptions.BadRequestException;
import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.exceptions.UnauthorizedOperationException;
import org.carpooling.helpers.constants.ModelNames;
import org.carpooling.helpers.constants.TravelStatus;
import org.carpooling.helpers.constants.attribute_constants.CandidateAttributes;
import org.carpooling.helpers.constants.attribute_constants.PassengerAttributes;
import org.carpooling.helpers.constants.attribute_constants.TravelAttributes;
import org.carpooling.models.*;
import org.springframework.data.domain.Page;


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

    public static boolean isLatLongEmpty(TravelPoint startingPoint) {
        return startingPoint.getLatitude() == null && startingPoint.getLongitude() == null;
    }

    public static boolean isPassengerInTravel(Travel travel, Passenger passengerToResign) {
        if (!travel.getPassengers().contains(passengerToResign)) {
            throw new EntityNotFoundException(
                    ModelNames.PASSENGER.toString(),
                    PassengerAttributes.USER_ID.toString(),
                    String.valueOf(passengerToResign.getUserId()),
                    ModelNames.TRAVEL.toString(),
                    TravelAttributes.ID.toString(),
                    String.valueOf(travel.getId())
                    );
        }
        return true;
    }
}
