package com.ekhonni.backend.exception.payment;

import com.ekhonni.backend.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * Author: Asif Iqbal
 * Date: 1/9/25
 */
@ControllerAdvice
public class PaymentExceptionHandler {

    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTransactionException(InvalidTransactionException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(InitiatePaymentException.class)
    public ResponseEntity<ErrorResponse> handleInitiatePaymentException(InitiatePaymentException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTransactionNotFoundException(TransactionNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(NoResponseException.class)
    public ResponseEntity<ErrorResponse> handleNoResponseException(NoResponseException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(ApiConnectionException.class)
    public ResponseEntity<ErrorResponse> handleApiConnectionException(ApiConnectionException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(404).body(response);
    }

}
