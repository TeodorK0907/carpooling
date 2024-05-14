package org.carpooling.helpers.model_filters;

import java.util.Optional;

public class UserFilterOptions {
    private Optional<String> username;
    private Optional<String> email;
    private Optional<String> phoneNumber;

    public UserFilterOptions(String username,
                                  String email,
                                  String firstName) {
        this.username = Optional.ofNullable(username);
        this.email = Optional.ofNullable(email);
        this.phoneNumber = Optional.ofNullable(firstName);
    }

    public Optional<String> getUsername() {
        return username;
    }

    public void setUsername(Optional<String> username) {
        this.username = username;
    }

    public Optional<String> getEmail() {
        return email;
    }

    public void setEmail(Optional<String> email) {
        this.email = email;
    }

    public Optional<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Optional<String> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
