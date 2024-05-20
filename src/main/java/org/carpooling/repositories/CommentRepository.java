package org.carpooling.repositories;

import org.carpooling.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("select c from Comment c where c.travel_id = :travelId")
    Optional<Comment> findByTravelId(int travelId);
}
