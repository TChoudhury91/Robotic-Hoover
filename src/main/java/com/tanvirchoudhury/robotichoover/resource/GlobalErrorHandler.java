package com.tanvirchoudhury.robotichoover.resource;

import com.tanvirchoudhury.robotichoover.exception.InvalidInputDataException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(InvalidInputDataException.class)
    public ResponseEntity handleException(InvalidInputDataException exception) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(exception.getMessage());
    }
}
