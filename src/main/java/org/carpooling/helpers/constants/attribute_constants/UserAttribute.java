package org.carpooling.helpers.constants.attribute_constants;

public enum UserAttribute {
    ID("id"),
    USERNAME("username"),
    PASSWORD("password"),
    FIRSTNAME("firstname"),
    LASTNAME("lastname"),
    EMAIL("email"),
    PHONE_NUMBER("phone number"),
    DRIVER_RATING("driver rating"),
    PASSENGER_RATING("passenger rating"),
    ROLE("role");

    private final String description;

    UserAttribute(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
