package com.tanvirchoudhury.robotichoover.resource;

import com.tanvirchoudhury.robotichoover.exception.InvalidInputDataException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(InvalidInputDataException.class)
    @ResponseStatus(BAD_REQUEST)
    public void handleException(InvalidInputDataException exception) {}
}
