package com.dylan.logintelligence.config;

import com.dylan.logintelligence.exceptions.InvalidLogEntityException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }

    @ExceptionHandler(InvalidLogEntityException.class)
    public ResponseEntity<?> handleInvalidLogEntityException(InvalidLogEntityException e) {
        return ResponseEntity.status(400).body("The log entry is invalid: " + e.getMessage());
    }
}
