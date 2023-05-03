package ru.kata.spring.boot_security.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<UserIncorrectID> handleException(NoSuchUserException exception) {
        UserIncorrectID userIncorrectID = new UserIncorrectID();
        userIncorrectID.setInfo(exception.getMessage());

        return new ResponseEntity<>(userIncorrectID, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserIncorrectID> handleException(Exception exception) {
        UserIncorrectID userIncorrectID = new UserIncorrectID();
        userIncorrectID.setInfo(exception.getMessage());

        return new ResponseEntity<>(userIncorrectID, HttpStatus.BAD_REQUEST);
    }
}
