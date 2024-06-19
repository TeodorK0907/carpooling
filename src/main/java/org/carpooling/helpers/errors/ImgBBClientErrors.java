package org.carpooling.helpers.errors;

public enum ImgBBClientErrors {

    FAILED_RESPONSE("The request could not be processed.");

    private final String description;

    ImgBBClientErrors(String description) {
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