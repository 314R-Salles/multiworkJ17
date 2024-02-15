package com.psalles.multiworkJ17.commons.exceptions;

public class TechnicalException extends  RuntimeException {
    private final String message;

    public TechnicalException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

