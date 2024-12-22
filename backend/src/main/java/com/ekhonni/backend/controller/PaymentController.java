package com.ekhonni.backend.controller;

import com.ekhonni.backend.payment.sslcommerz.SSLCommerzValidatorResponse;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * Author: Asif Iqbal
 * Date: 12/15/24
 */

@RestController
@RequestMapping("/api/v2/payment")
@Slf4j
public record PaymentController(PaymentService paymentService) {

    @PostMapping("/initiate/{bidlog_id}")
    public ApiResponse<?> initiatePayment(@PathVariable("bidlog_id") Long bidLogId) {
        return paymentService.initiatePayment(bidLogId);
    }

    @PostMapping("/success")
    public ApiResponse<?> success(@RequestParam Map<String, String> validatorResponse) {
        log.info("Validator Response: {}", validatorResponse);
        return new ApiResponse<>(true, "Success", "Payment successful.", HttpStatus.OK);
    }

    @PostMapping("/fail")
    public ApiResponse<?> fail() {
        return new ApiResponse<>(false, "Fail", "Payment failed.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/cancel")
    public ApiResponse<?> cancel() {
        return new ApiResponse<>(false, "Cancel", "Payment canceled.", HttpStatus.PAYMENT_REQUIRED);
    }

    @PostMapping("/ipn")
    public ApiResponse<?> handleIpn(@RequestBody SSLCommerzValidatorResponse ipnData) {
        return new ApiResponse<>(false, "Success", ipnData, HttpStatus.PAYMENT_REQUIRED);
    }

}
