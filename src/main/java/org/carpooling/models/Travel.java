package org.carpooling.models;

import jakarta.persistence.*;
import org.carpooling.helpers.constants.TravelStatus;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "travels", schema = "rose-valley-travel")
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
    @Column(name = "free_spots")
    private int free_spots;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "travel_status_id")
    private TravelStatus status;
    @Column(name = "duration")
    private Double duration;
    @Column(name = "distance")
    private Double distance;
    @Column(name = "is_archived")
    private boolean isArchived;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "travel_passengers",
            schema = "rose-valley-travel",
    joinColumns = @JoinColumn(name = "travel_id"),
    inverseJoinColumns = @JoinColumn(name = "passenger_id"))
    private Set<Passenger> passengers;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "travel_candidates",
            schema = "rose-valley-travel",
            joinColumns = @JoinColumn(name = "travel_id"),
            inverseJoinColumns = @JoinColumn(name = "candidate_id"))
    private Set<Candidate> candidates;

    public Travel () {
        this.candidates = new HashSet<>();
        this.passengers = new HashSet<>();
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

    public int getFree_spots() {
        return free_spots;
    }

    public void setFree_spots(int free_spots) {
        this.free_spots = free_spots;
    }

    public TravelStatus getStatus() {
        return status;
    }

    public void setStatus(TravelStatus status) {
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

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Set<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(Set<Candidate> candidates) {
        this.candidates = candidates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Travel travel)) return false;
        return id == travel.id
                && free_spots == travel.free_spots
                && isArchived == travel.isArchived
                && Objects.equals(creator, travel.creator)
                && Objects.equals(startingTravelPoint, travel.startingTravelPoint)
                && Objects.equals(endingTravelPoint, travel.endingTravelPoint)
                && Objects.equals(departureTime, travel.departureTime)
                && status == travel.status
                && Objects.equals(duration, travel.duration)
                && Objects.equals(distance, travel.distance)
                && Objects.equals(passengers, travel.passengers)
                && Objects.equals(candidates, travel.candidates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creator, startingTravelPoint,
                endingTravelPoint, departureTime, free_spots,
                status, duration, distance, isArchived,
                passengers, candidates);
    }
}