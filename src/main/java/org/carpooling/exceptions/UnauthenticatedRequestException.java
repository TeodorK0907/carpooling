package org.carpooling.exceptions;

public class UnauthenticatedRequestException extends RuntimeException {
    public UnauthenticatedRequestException(String message) {
        super(message);
    }
}
