package com.fleet.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class FleetExistsException extends Exception {
    private static final Long serialVersionUID = 1L;

    public FleetExistsException(String message) {
        super(message);
    }
}
