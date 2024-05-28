package org.carpooling.helpers.validators;

import org.carpooling.exceptions.BadRequestException;
import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.exceptions.UnauthorizedOperationException;
import org.carpooling.helpers.constants.TravelStatus;
import org.carpooling.helpers.errors.TravelValidatorErrors;
import org.carpooling.models.Travel;
import org.carpooling.models.TravelPoint;
import org.carpooling.models.User;
import org.springframework.data.domain.Page;

import static org.carpooling.helpers.constants.ModelNames.TRAVEL;
import static org.carpooling.helpers.constants.attribute_constants.UserAttribute.ID;


public class TravelValidator {

    public static boolean isTravelListEmpty(Page<Travel> list) {
        if (list.getContent().isEmpty()) {
            throw new EntityNotFoundException(TravelValidatorErrors.NO_TRAVELS_FOUND.toString());
        }
        return false;
    }


    public static boolean isStatusActive(Travel travel) {
        if (travel.getStatus() != TravelStatus.ACTIVE) {
            throw new BadRequestException(TravelValidatorErrors.STATUS_NOT_ACTIVE.toString());
        }
        return true;
    }

    public static boolean isUserCreatorOfTravel(User authenticatedUser, User creator) {
        if (!authenticatedUser.equals(creator)) {
            throw new UnauthorizedOperationException(TravelValidatorErrors.UNAUTHORIZED.toString());
        }
        return true;
    }

    public static boolean isCreatorOfTravelCurrentUser(User authenticatedUser, User creator) {
        return authenticatedUser.equals(creator);
    }

    public static boolean isStatusCancelable(Travel toBeMarkedCancelled) {
        switch (toBeMarkedCancelled.getStatus()) {
            case ACTIVE, PLANNED:
                return true;
            default:
                throw new BadRequestException(TravelValidatorErrors.STATUS_NOT_CANCELABLE.toString());
        }
    }

    public static boolean isLatLongEmpty(TravelPoint startingPoint) {
        return startingPoint.getLatitude() == null && startingPoint.getLongitude() == null;
    }

    public static void isStatusCompleted(Travel travel) {
        if (travel.getStatus() != TravelStatus.COMPLETED) {
            throw new BadRequestException(TravelValidatorErrors.STATUS_NOT_COMPLETED.toString());
        }
    }

    public static boolean isTravelDeleted(Travel travel) {
        if (travel.isArchived()) {
            throw new EntityNotFoundException(TRAVEL.toString(), ID.toString(), String.valueOf(travel.getId()));
        }
        return false;
    }
}