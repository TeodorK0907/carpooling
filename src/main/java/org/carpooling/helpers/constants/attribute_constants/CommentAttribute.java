package org.carpooling.helpers.constants.attribute_constants;

public enum CommentAttribute {
    ID("id"),
    TRAVEL_ID("travelId"),
    CONTENT("content");


    private final String description;

    CommentAttribute(String description) {
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
