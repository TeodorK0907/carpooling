package org.carpooling.helpers;

public enum UserRole {
    USER("User"),
    ADMIN("Admin");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return description;
    }
}
