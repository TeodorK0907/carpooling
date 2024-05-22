package org.carpooling.services.contracts;

import org.carpooling.models.Passenger;
import org.carpooling.models.User;

public interface PassengerService {
    Passenger create(User user);

    Passenger getByUserId(int userId);

    void delete(Passenger passenger);
}
