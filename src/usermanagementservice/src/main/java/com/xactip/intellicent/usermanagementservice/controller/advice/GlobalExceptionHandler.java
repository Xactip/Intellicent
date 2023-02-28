package com.xactip.intellicent.usermanagementservice.controller.advice;

import com.xactip.intellicent.usermanagementservice.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(UserNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }
}
