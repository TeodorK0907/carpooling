package org.carpooling.models;

import jakarta.persistence.*;
import org.carpooling.helpers.constants.TravelStatus;

import java.time.LocalDateTime;
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
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "passenger_id")
    private Set<Passenger> passengers;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "candidate_id")
    private Set<Candidate> candidates;

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
}
