package org.carpooling.repositories;

import org.carpooling.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {
    Optional<Passenger> findByUserIdAndTravelId(int userId, int TravelId);
}
