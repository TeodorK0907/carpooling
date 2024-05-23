package org.carpooling.services.contracts;

import org.carpooling.models.Passenger;
import org.carpooling.models.User;

public interface PassengerService {
    Passenger approve(User user, Passenger passenger, int travelId);

    Passenger getById(int passengerId);

    Passenger getByUserAndTravelId(int userId, int travelId);

    void decline(User user, int passengerId, int travelId);
}
