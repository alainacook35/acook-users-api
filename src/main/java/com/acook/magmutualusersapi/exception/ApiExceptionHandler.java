package com.acook.magmutualusersapi.exception;

import org.springframework.data.core.PropertyReferenceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<String> handleBadSort(PropertyReferenceException ex) {
        return ResponseEntity.badRequest().body("Invalid sort field: " + ex.getPropertyName());
    }
}
