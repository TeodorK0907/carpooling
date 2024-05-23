package org.carpooling.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "candidates", schema = "rose-valley-travel")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidate_id")
    private int id;
    @JsonIgnore
    @Column(name = "user_id")
    private int userId;
    @Column(name = "travel_id")
    private int travelId;
    @Column(name = "username")
    private String username;

    public Candidate() {
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

    public int getTravelId() {
        return travelId;
    }

    public void setTravelId(int travelId) {
        this.travelId = travelId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Candidate candidate)) return false;
        return id == candidate.id
                && userId == candidate.userId
                && travelId == candidate.travelId
                && Objects.equals(username, candidate.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, travelId, username);
    }
}
