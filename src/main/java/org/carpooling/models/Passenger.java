package org.carpooling.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "passengers", schema = "rose-valley-travel")
public class Passenger extends Candidate {
    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "passenger_id")
//    private int id;
//    @JsonIgnore
//    @Column(name = "user_id")
//    private int userId;
//    @Column(name = "travel_id")
//    private int travelId;
//    @Column(name = "username")
//    private String username;
    @Column(name = "gave_rating")
    private boolean gaveRating;
    @Column(name = "given_rating")
    private boolean givenRating;

    public Passenger() {
    }

    public boolean isGaveRating() {
        return gaveRating;
    }

    public void setGaveRating(boolean gaveRating) {
        this.gaveRating = gaveRating;
    }

    public boolean isGivenRating() {
        return givenRating;
    }

    public void setGivenRating(boolean givenRating) {
        this.givenRating = givenRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passenger passenger)) return false;
        return gaveRating == passenger.gaveRating
                && givenRating == passenger.givenRating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gaveRating, givenRating);
    }
}
