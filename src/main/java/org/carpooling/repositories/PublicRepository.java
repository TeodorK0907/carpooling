package org.carpooling.repositories;

import org.carpooling.models.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PublicRepository extends UserRepository {
    @Query("select u from User u " +
            "join u.driverRating as rating " +
            "order by rating.avgRating desc " +
            "limit 10")
    List<User> getTopTenDrivers();

    @Query("select u from User u " +
            "join u.passengerRating as rating " +
            "order by rating.avgRating desc " +
            "limit 10")
    List<User> getTopTenPassengers();
}
