package org.carpooling.repositories;

import org.carpooling.helpers.constants.TravelStatus;
import org.carpooling.models.Travel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TravelRepository extends JpaRepository<Travel, Integer> {

    List<Travel> findAllByStatusIs(TravelStatus status);
    @Query("select t from Travel t "
            + "where (:startLocation='' or t.startingTravelPoint.address like :startLocation) "
            + "and (:endLocation='' or t.endingTravelPoint.address like :endLocation) "
            + "and (:driver='' or t.creator.username like :driver) "
            + "and t.departureTime >= :departAfter "
            + "and t.departureTime <= :departBefore "
            + "and t.free_spots >= :freeSpots"
    )
    Page<Travel> findAllWithFilter(@Param("startLocation")String startLocation,
                                   @Param("endLocation")String endLocation,
                                   @Param("driver") String driver,
                                   @Param("departAfter") LocalDateTime departAfter,
                                   @Param("departBefore") LocalDateTime departBefore,
                                   @Param("freeSpots") Integer freeSpots,
                                   Pageable page);
}
