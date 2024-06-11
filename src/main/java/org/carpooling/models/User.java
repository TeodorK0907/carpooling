package org.carpooling.models;

import jakarta.persistence.*;
import org.carpooling.helpers.constants.UserRole;

import java.util.Objects;

@Entity
@Table(name = "users", schema = "rose-valley-travel")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_rating_id", referencedColumnName = "feedback_id")
    private Rating driverRating;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "passenger_rating_id", referencedColumnName = "feedback_id")
    private Rating passengerRating;
    @Enumerated
    @Column(name = "role_id")
    private UserRole role;
    @Column(name = "is_blocked")
    private boolean blocked;
    @Column(name = "is_archived")
    private boolean archived;
    @Column(name = "profile_picture")
    private String profilePicture;
    @Column(name = "is_verified")
    private boolean isVerified;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Rating getDriverRating() {
        return driverRating;
    }

    public void setDriverRating(Rating driverRating) {
        this.driverRating = driverRating;
    }

    public Rating getPassengerRating() {
        return passengerRating;
    }

    public void setPassengerRating(Rating passengerRating) {
        this.passengerRating = passengerRating;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id == user.id
                && blocked == user.blocked
                && archived == user.archived
                && isVerified == user.isVerified
                && Objects.equals(username, user.username)
                && Objects.equals(password, user.password)
                && Objects.equals(firstname, user.firstname)
                && Objects.equals(lastname, user.lastname)
                && Objects.equals(email, user.email)
                && Objects.equals(phoneNumber, user.phoneNumber)
                && Objects.equals(driverRating, user.driverRating)
                && Objects.equals(passengerRating, user.passengerRating)
                && Objects.equals(role, user.role)
                && Objects.equals(profilePicture, user.profilePicture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password,
                firstname, lastname, email,
                phoneNumber, driverRating, passengerRating,
                role, blocked, archived, profilePicture, isVerified);
    }
}
