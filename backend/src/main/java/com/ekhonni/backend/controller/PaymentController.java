package com.ekhonni.backend.controller;

import com.ekhonni.backend.exception.InitiatePaymentException;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Author: Asif Iqbal
 * Date: 12/15/24
 */

@RestController
@RequestMapping("/api/v2/payment")
@Slf4j
public record PaymentController(PaymentService paymentService) {

    @PostMapping("/initiate/{bid_log_id}")
    public ApiResponse<?> initiatePayment(@PathVariable("bid_log_id") Long bidLogId)  {
        try {
            return new ApiResponse<>(true, "Success", paymentService.initiatePayment(bidLogId), HttpStatus.OK);
        } catch (Exception e) {
            throw new InitiatePaymentException("Internal server error");
        }
    }

    @PostMapping("/success")
    public ApiResponse<?> success(@RequestParam Map<String, String> validatorResponse) {
        log.info("Success Response: {}", validatorResponse);
        return new ApiResponse<>(true, "Success", validatorResponse, HttpStatus.OK);
    }

    @PostMapping("/fail")
    public ApiResponse<?> fail(@RequestParam Map<String, String> validatorResponse) {
        log.info("Fail Response: {}", validatorResponse);
        return new ApiResponse<>(false, "Fail", "Payment failed.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/cancel")
    public ApiResponse<?> cancel(@RequestParam Map<String, String> validatorResponse) {
        log.info("Cancel Response: {}", validatorResponse);
        return new ApiResponse<>(false, "Cancel", "Payment canceled.", HttpStatus.PAYMENT_REQUIRED);
    }

    @PostMapping("/ipn")
    public ApiResponse<?> handleIpn(@RequestParam Map<String, String> ipnResponse, HttpServletRequest request) {
        paymentService.verifyTransaction(ipnResponse, request);
        return new ApiResponse<>(true, "Success", null, HttpStatus.OK);
    }

}
