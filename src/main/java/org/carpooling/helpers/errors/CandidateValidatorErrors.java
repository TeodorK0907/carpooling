package org.carpooling.helpers.errors;

public enum CandidateValidatorErrors {
    UNAUTHORIZED("You are unauthorized to perform the required action.");

    private final String description;

    CandidateValidatorErrors(String description) {
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
