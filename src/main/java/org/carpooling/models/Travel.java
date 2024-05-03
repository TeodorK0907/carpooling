package org.carpooling.models;

import jakarta.persistence.*;
import org.carpooling.helpers.TravelStatus;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "travels")
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User creator;
    @ManyToOne
    @JoinColumn(name = "starting_point")
    private TravelPoint startingTravelPoint;
    @ManyToOne
    @JoinColumn(name = "ending_point")
    private TravelPoint endingTravelPoint;
    @Column(name = "departure_time")
    private LocalDateTime departureTime;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "travel_status_id")
    private TravelStatus status;

    public Travel () {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public TravelPoint getStartingPoint() {
        return startingTravelPoint;
    }

    public void setStartingPoint(TravelPoint startingTravelPoint) {
        this.startingTravelPoint = startingTravelPoint;
    }

    public TravelPoint getEndingPoint() {
        return endingTravelPoint;
    }

    public void setEndingPoint(TravelPoint endingTravelPoint) {
        this.endingTravelPoint = endingTravelPoint;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public TravelStatus getStatus() {
        return status;
    }

    public void setStatus(TravelStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Travel travel)) return false;
        return id == travel.id
                && Objects.equals(creator, travel.creator)
                && Objects.equals(startingTravelPoint, travel.startingTravelPoint)
                && Objects.equals(endingTravelPoint, travel.endingTravelPoint)
                && Objects.equals(departureTime, travel.departureTime)
                && status == travel.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creator, startingTravelPoint, endingTravelPoint, departureTime, status);
    }
}
