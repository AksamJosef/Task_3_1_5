package ru.kata.spring.boot_security.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<UserIncorrectInfo> handleException(Exception exception) {
        UserIncorrectInfo userIncorrectInfo = new UserIncorrectInfo();
        userIncorrectInfo.setInfo(exception.getMessage());

        return new ResponseEntity<>(userIncorrectInfo, HttpStatus.BAD_REQUEST);
    }
}
