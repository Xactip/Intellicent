package com.xactip.intellicent.knowledgebaseservice.exception;

public class ReferenceNotFoundException extends RuntimeException {
    public ReferenceNotFoundException(String message) {
        super(message);
    }

    public ReferenceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
