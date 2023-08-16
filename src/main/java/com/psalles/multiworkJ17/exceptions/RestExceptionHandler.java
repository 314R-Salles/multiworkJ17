package com.psalles.multiworkJ17.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(Exception ex, WebRequest request) {
        return new ResponseEntity<Object>("unauthorized", null, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> handleForbiddenException(Exception ex, WebRequest request) {
        return new ResponseEntity<Object>("forbidden", null, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<Object> handleTechnicalException(Exception ex, WebRequest request) {
        return new ResponseEntity<Object>("boom", null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(ex.getMessage(), null, HttpStatus.NOT_FOUND);
    }

}
