package org.carpooling.services;

import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.helpers.constants.ModelNames;
import org.carpooling.helpers.constants.attribute_constants.CandidateAttributes;
import org.carpooling.helpers.constants.attribute_constants.PassengerAttributes;
import org.carpooling.helpers.validators.TravelValidator;
import org.carpooling.models.Passenger;
import org.carpooling.models.Travel;
import org.carpooling.models.User;
import org.carpooling.repositories.PassengerRepository;
import org.carpooling.services.contracts.PassengerService;
import org.carpooling.services.contracts.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final TravelService travelService;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository,
                                TravelService travelService) {
        this.passengerRepository = passengerRepository;
        this.travelService = travelService;
    }

    @Override
    public Passenger approve(User user, Passenger passenger, int travelId) {
        Travel travel = travelService.getById(travelId);
        TravelValidator.isUserCreatorOfTravel(user, travel.getCreator());
        //todo finish below method body
        return passenger;
    }

    @Override
    public Passenger getById(int passengerId) {
        return passengerRepository.findById(passengerId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ModelNames.PASSENGER.toString(),
                        PassengerAttributes.ID.toString(),
                        String.valueOf(passengerId)
                ));
    }

    @Override
    public Passenger getByUserAndTravelId(int userId, int travelId) {
        return passengerRepository.findByUserIdAndTravelId(userId, travelId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ModelNames.PASSENGER.toString(),
                        CandidateAttributes.USER_ID.toString(),
                        String.valueOf(userId)
                ));
    }

    @Override
    public void decline(User user, int passengerId, int travelId) {
        Travel travel = travelService.getById(travelId);
        TravelValidator.isUserCreatorOfTravel(user, travel.getCreator());
        Passenger toRemove = getById(passengerId);
        //todo finish rest of method
       // travel.getPassengers().remove(pas)

    }
}
