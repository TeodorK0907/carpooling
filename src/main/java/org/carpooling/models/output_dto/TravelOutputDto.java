package org.carpooling.models.output_dto;

import org.carpooling.models.Candidate;
import org.carpooling.models.Passenger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TravelOutputDto {
    private int travelId;
    private String driverName;
    private String startingPoint;
    private String endingPoint;
    private LocalDateTime departureTime;
    private int freeSpots;
    private String status;
    private Double duration;
    private Double distance;
    private final List<Candidate> candidates;
    private final List<Passenger> passengers;
    private String comment;

    public TravelOutputDto() {
        this.candidates = new ArrayList<>();
        this.passengers = new ArrayList<>();
    }

    public int getTravelId() {
        return travelId;
    }

    public void setTravelId(int travelId) {
        this.travelId = travelId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getEndingPoint() {
        return endingPoint;
    }

    public void setEndingPoint(String endingPoint) {
        this.endingPoint = endingPoint;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public int getFreeSpots() {
        return freeSpots;
    }

    public void setFreeSpots(int freeSpots) {
        this.freeSpots = freeSpots;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public List<Candidate> getCandidates() {
        return this.candidates;
    }


    public List<Passenger> getPassengers() {
        return this.passengers;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TravelOutputDto that)) return false;
        return travelId == that.travelId
                && freeSpots == that.freeSpots
                && Objects.equals(driverName, that.driverName)
                && Objects.equals(startingPoint, that.startingPoint)
                && Objects.equals(endingPoint, that.endingPoint)
                && Objects.equals(departureTime, that.departureTime)
                && Objects.equals(status, that.status)
                && Objects.equals(duration, that.duration)
                && Objects.equals(distance, that.distance)
                && Objects.equals(candidates, that.candidates)
                && Objects.equals(passengers, that.passengers)
                && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(travelId, driverName, startingPoint,
                endingPoint, departureTime, freeSpots, status,
                duration, distance, candidates, passengers, comment);
    }
}