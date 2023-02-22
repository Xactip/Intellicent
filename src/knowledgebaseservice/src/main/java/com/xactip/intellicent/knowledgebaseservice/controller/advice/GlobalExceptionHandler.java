package com.xactip.intellicent.knowledgebaseservice.controller.advice;

import com.xactip.intellicent.knowledgebaseservice.exception.EntityNotFoundException;
import com.xactip.intellicent.knowledgebaseservice.exception.ReferenceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReferenceNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(ReferenceNotFoundException ex) {
        Map<String, List<String>> errors = Map.of("errors", Collections.singletonList(ex.getMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(EntityNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }
}
