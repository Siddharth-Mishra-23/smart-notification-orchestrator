package com.notification.orchestrator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class SystemOverloadedException extends RuntimeException {

    public SystemOverloadedException(String message) {
        super(message);
    }
}