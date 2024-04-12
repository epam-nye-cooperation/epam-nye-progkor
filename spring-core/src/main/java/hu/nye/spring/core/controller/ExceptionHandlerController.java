package hu.nye.spring.core.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import hu.nye.spring.core.exception.UserNotFoundException;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User not found in the db.")
    @ExceptionHandler(UserNotFoundException.class)
    public void handleUserNotFoundException() {

    }
}
