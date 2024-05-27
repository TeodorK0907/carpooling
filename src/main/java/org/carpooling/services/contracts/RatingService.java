package org.carpooling.services.contracts;

import org.carpooling.models.Rating;
import org.carpooling.models.User;

public interface RatingService {
    Rating createRating();

    Rating givePassengerRating(User authenticatedUser, int passengerId,
                               int travelId, int ratingToReceive);

    Rating giveDriverRating(User authenticatedUser, int travelId, int ratingToReceive);
}
