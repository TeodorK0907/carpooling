package org.carpooling.services.contracts;

import org.carpooling.models.User;

import java.util.List;

public interface PublicService {
    List<User> getTopTenDrivers();

    List<User> getTopTenPassengers();
}
