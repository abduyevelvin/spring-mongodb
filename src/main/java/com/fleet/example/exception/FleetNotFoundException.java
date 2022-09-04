package com.fleet.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FleetNotFoundException extends Exception {
    private static final Long serialVersionUID = 1L;

    public FleetNotFoundException(String message) {
        super(message);
    }
}
