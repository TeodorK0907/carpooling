package org.carpooling.repositories;

import org.carpooling.models.TravelPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointRepository extends JpaRepository<TravelPoint, Integer> {

     Optional<TravelPoint> findByAddress(String address);
}
