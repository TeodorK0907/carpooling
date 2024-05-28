package org.carpooling.models.input_dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class RatingDto {
    private static final int LOWEST_RATING = 0;
    private static final int HIGHEST_RATING = 5;
    @NotNull
    @Min(value = LOWEST_RATING)
    @Max(value = HIGHEST_RATING)
    private int rating;

    public RatingDto() {

    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RatingDto ratingDto)) return false;
        return rating == ratingDto.rating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rating);
    }
}
