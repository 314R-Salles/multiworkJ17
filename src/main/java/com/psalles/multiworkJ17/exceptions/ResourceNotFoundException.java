package com.psalles.multiworkJ17.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    private final String message;

    public ResourceNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
