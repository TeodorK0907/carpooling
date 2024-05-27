package org.carpooling.exceptions;

public class DuplicateRequestException extends RuntimeException {
    public DuplicateRequestException(String type, String attribute, String value, String action,
                                     String type2, String type3, String attribute2, String value2) {
        super(String.format("%s, with %s %s has already %s %s for %s with %s %s.",
                type, attribute, value, action,  type2, type3, attribute2, value2));
    }
}