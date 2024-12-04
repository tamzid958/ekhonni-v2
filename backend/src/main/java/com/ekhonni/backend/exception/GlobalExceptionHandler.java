package com.ekhonni.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userNotFoundException.getMessage());
    }


    @ExceptionHandler({UserAlreadyDeletedException.class})
    public ResponseEntity<Object> handleUserAlreadyDeletedException(UserAlreadyDeletedException userAlreadyDeletedException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userAlreadyDeletedException.getMessage());
    }
}
