package com.psalles.multiworkJ17.exceptions;

public class ForbiddenException extends  RuntimeException {
    private final String message;

    public ForbiddenException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

