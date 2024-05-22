package org.carpooling.services;

import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.helpers.constants.ModelNames;
import org.carpooling.helpers.constants.attribute_constants.CandidateAttributes;
import org.carpooling.models.Passenger;
import org.carpooling.models.User;
import org.carpooling.repositories.PassengerRepository;
import org.carpooling.services.contracts.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public Passenger create(User user) {
        Passenger passenger = new Passenger();
        passenger.setUserId(user.getId());
        passenger.setUsername(user.getUsername());
        return passenger;
    }

    @Override
    public Passenger getByUserId(int userId) {
       return passengerRepository.findByUserId(userId)
               .orElseThrow(() -> new EntityNotFoundException(
                       ModelNames.CANDIDATE.toString(),
                       CandidateAttributes.USER_ID.toString(),
                       String.valueOf(userId)
               ));
    }

    @Override
    public void delete(Passenger passenger) {
    }
}
