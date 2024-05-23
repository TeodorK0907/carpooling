package org.carpooling.helpers.validators;

import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.helpers.constants.ModelNames;
import org.carpooling.helpers.constants.attribute_constants.PassengerAttributes;
import org.carpooling.helpers.constants.attribute_constants.TravelAttributes;
import org.carpooling.models.Candidate;
import org.carpooling.models.Travel;

public class CandidateValidator {
    public static boolean isCandidaterInTravel(Travel travel, Candidate candidate) {
        if (!travel.getCandidates().contains(candidate)) {
            throw new EntityNotFoundException(
                    ModelNames.CANDIDATE.toString(),
                    PassengerAttributes.USER_ID.toString(),
                    String.valueOf(candidate.getUserId()),
                    ModelNames.TRAVEL.toString(),
                    TravelAttributes.ID.toString(),
                    String.valueOf(travel.getId())
            );
        }
        return true;
    }
}
