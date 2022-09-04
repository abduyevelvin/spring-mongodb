package com.fleet.example.controller;

import com.fleet.example.dto.ErrorDTO;
import com.fleet.example.exception.FleetExistsException;
import com.fleet.example.exception.FleetNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({FleetNotFoundException.class})
    public ResponseEntity<ErrorDTO> handle(FleetNotFoundException fleetNotFoundException) {
        ErrorDTO error = ErrorDTO.builder()
                .message("Not Found Exception")
                .details(List.of(fleetNotFoundException.getMessage()))
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({FleetExistsException.class})
    public ResponseEntity<ErrorDTO> handle(FleetExistsException fleetExistsException) {
        ErrorDTO error = ErrorDTO.builder()
                .message("DB Conflict")
                .details(List.of(fleetExistsException.getMessage()))
                .build();

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({DuplicateKeyException.class})
    protected ResponseEntity<ErrorDTO> handle(DuplicateKeyException ex) {
        ErrorDTO error = ErrorDTO.builder()
                .message("Validation Failed")
                .details(List.of(Objects.requireNonNull(ex.getRootCause()).getLocalizedMessage()))
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorDTO error = ErrorDTO.builder()
                .message("Validation Failed")
                .details(details)
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
