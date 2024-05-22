package org.carpooling.helpers.constants.attribute_constants;

public enum CandidateAttributes {
    ID("id"),
    USER_ID("userId"),
    USERNAME("username");


    private final String description;

    CandidateAttributes(String description) {
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
