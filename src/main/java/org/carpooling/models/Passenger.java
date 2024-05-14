package org.carpooling.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;
@Entity
@Table(name = "passengers", schema = "rose-valley-travel")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id")
    private int id;
    @JsonIgnore
    @Column(name = "user_id")
    private int userId;
    @Column(name = "username")
    private String username;
    @Column(name = "gave_rating")
    private boolean gaveRating;
    @Column(name = "given_rating")
    private boolean givenRating;

    public Passenger() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        return id == passenger.id
                && userId == passenger.userId
                && gaveRating == passenger.gaveRating
                && givenRating == passenger.givenRating
                && Objects.equals(username, passenger.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, username, gaveRating, givenRating);
    }
}
