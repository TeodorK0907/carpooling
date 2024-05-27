package org.carpooling.services;

import org.carpooling.exceptions.BadRequestException;
import org.carpooling.helpers.validators.PassengerValidator;
import org.carpooling.helpers.validators.RatingValidator;
import org.carpooling.helpers.validators.TravelValidator;
import org.carpooling.models.Passenger;
import org.carpooling.models.Rating;
import org.carpooling.models.Travel;
import org.carpooling.models.User;
import org.carpooling.repositories.RatingRepository;
import org.carpooling.services.contracts.PassengerService;
import org.carpooling.services.contracts.RatingService;
import org.carpooling.services.contracts.TravelService;
import org.carpooling.services.contracts.UserService;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {
    private static final int NEW_RATING = 1;
    private final RatingRepository ratingRepository;
    private final TravelService travelService;
    private final PassengerService passengerService;
    private final UserService userService;

    public RatingServiceImpl(RatingRepository ratingRepository,
                             TravelService travelService,
                             PassengerService passengerService,
                             UserService userService) {
        this.ratingRepository = ratingRepository;
        this.travelService = travelService;
        this.passengerService = passengerService;
        this.userService = userService;
    }

    @Override
    public Rating createRating() {
        Rating rating = new Rating();
        ratingRepository.save(rating);
        return rating;
    }

    @Override
    public Rating givePassengerRating(User authenticatedUser, int passengerId,
                                      int travelId, int ratingToReceive) {
        Passenger passenger = passengerService.getById(passengerId);
        Travel travel = travelService.getById(travelId);
        TravelValidator.isUserCreatorOfTravel(authenticatedUser, travel.getCreator());
        TravelValidator.isStatusCompleted(travel);
        PassengerValidator.isPassengerInTravel(travel, passenger);
        if (PassengerValidator.isUserPassenger(authenticatedUser, passenger)) {
            RatingValidator.isPassengerAlreadyGivenRating(passenger, travelId);
            User toReceiveRating = userService.getById(passenger.getUserId());
            Rating rating = updatePassengerRating(toReceiveRating, passenger, ratingToReceive);
            //todo update passenger
            passengerService.update(passenger);
            ratingRepository.save(rating);
            return rating;
        }
        //todo convert below magic string to constant
        throw new BadRequestException("it is not allowed for users to give feedback to themselves.");
    }

    @Override
    public Rating giveDriverRating(User authenticatedUser, int travelId, int ratingToReceive) {
        Travel travel = travelService.getById(travelId);
        TravelValidator.isStatusCompleted(travel);
        if (!TravelValidator.isCreatorOfTravelCurrentUser(authenticatedUser, travel.getCreator())) {
            Passenger passenger = passengerService.getByUserAndTravelId(
                    authenticatedUser.getId(), travelId);
            RatingValidator.hasPassengerAlreadyGivenRating(passenger, travelId);
            Rating rating = updateDriverRating(passenger, authenticatedUser, ratingToReceive);
            //todo update passenger
            passengerService.update(passenger);
            ratingRepository.save(rating);
            return rating;
        }
        //todo convert below magic string to constant
        throw new BadRequestException("it is not allowed for users to give feedback to themselves.");
    }

    private Rating updatePassengerRating(User toGetFeedback, Passenger isGivenFeedback, int ratingToReceive) {
        isGivenFeedback.setGivenRating(true);
        toGetFeedback.setPassengerRating(calculateNewRating(toGetFeedback.getPassengerRating(), ratingToReceive));
        return toGetFeedback.getPassengerRating();
    }

    private Rating updateDriverRating(Passenger wilGiveFeedback, User toGetFeedback, int ratingToReceive) {
        wilGiveFeedback.setGaveRating(true);
        toGetFeedback.setDriverRating(calculateNewRating(toGetFeedback.getDriverRating(), ratingToReceive));
        return toGetFeedback.getDriverRating();
    }

    private Rating calculateNewRating(Rating rating, int ratingToReceive) {
        rating.setAvgRating((rating.getAvgRating() + ratingToReceive) / rating.getTotalFeedbacks());
        rating.setTotalFeedbacks(rating.getTotalFeedbacks() + NEW_RATING);
        return rating;
    }
}