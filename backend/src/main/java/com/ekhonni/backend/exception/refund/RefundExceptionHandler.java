package com.ekhonni.backend.exception.refund;

import com.ekhonni.backend.exception.ErrorResponse;
import com.ekhonni.backend.exception.payment.InitiatePaymentException;
import com.ekhonni.backend.exception.payment.InvalidTransactionException;
import com.ekhonni.backend.exception.payment.TransactionNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * Author: Asif Iqbal
 * Date: 1/9/25
 */

@ControllerAdvice
public class RefundExceptionHandler {

    @ExceptionHandler(InvalidRefundRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRefundRequestException(InvalidRefundRequestException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(RefundNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRefundNotFoundException(RefundNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(NoResponseException.class)
    public ResponseEntity<ErrorResponse> handleNoResponseException(NoResponseException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(ApiConnectionException.class)
    public ResponseEntity<ErrorResponse> handleApiConnectionException(ApiConnectionException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(RefundRequestFailedException.class)
    public ResponseEntity<ErrorResponse> handleRefundRequestFailedException(RefundRequestFailedException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(400).body(response);
    }

}
