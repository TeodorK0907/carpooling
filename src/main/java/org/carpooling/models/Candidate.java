package org.carpooling.models;

import jakarta.persistence.*;

import java.util.Objects;
@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidate_id")
    private int id;
    @JoinColumn("user_id")
    private int userId;
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
                && Objects.equals(username, candidate.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, username);
    }
}
