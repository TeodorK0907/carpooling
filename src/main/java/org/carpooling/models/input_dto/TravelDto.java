package org.carpooling.models.input_dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.carpooling.helpers.errors.InputErrors;
import org.carpooling.helpers.errors.TravelDtoErrors;

public class TravelDto {
    @NotEmpty(message = InputErrors.FIELD_EMPTY)
    private String startingPoint;
    @NotEmpty(message = InputErrors.FIELD_EMPTY)
    private String endPoint;
    @NotEmpty(message = InputErrors.FIELD_EMPTY)
    @Size(min = 16, message = TravelDtoErrors.TIME)
    private String departureTime;
    @NotNull
    @Min(value = 1, message = TravelDtoErrors.FREE_SPOTS)
    private int freeSpots;
    private String comment;

    public TravelDto() {
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getFreeSpots() {
        return freeSpots;
    }

    public void setFreeSpots(int freeSpots) {
        this.freeSpots = freeSpots;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
