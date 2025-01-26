package com.ekhonni.backend.exception.bid;

import com.ekhonni.backend.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * Author: Asif Iqbal
 * Date: 1/9/25
 */

public class BidExceptionHandler {

    @ExceptionHandler(BidNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBidNotFoundException(BidNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(InvalidBidAmountException.class)
    public ResponseEntity<ErrorResponse> handleInvalidBidAmountException(InvalidBidAmountException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(BidCurrencyMismatchException.class)
    public ResponseEntity<ErrorResponse> handleBidCurrencyMismatchException(BidCurrencyMismatchException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(BidCreationException.class)
    public ResponseEntity<ErrorResponse> handleBidCreationException(BidCreationException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(400).body(response);
    }

}
