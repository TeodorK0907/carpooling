package org.carpooling.services;

import org.carpooling.models.Rating;
import org.carpooling.repositories.RatingRepository;
import org.carpooling.services.contracts.RatingService;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Rating createRating() {
        Rating rating = new Rating();
        ratingRepository.save(rating);
        return rating;
    }

}
