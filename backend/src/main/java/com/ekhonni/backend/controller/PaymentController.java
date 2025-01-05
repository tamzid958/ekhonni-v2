package com.ekhonni.backend.controller;

import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.exception.InitiatePaymentException;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Payment", description = "Manage payment operations")
public record PaymentController(PaymentService paymentService) {

    @PostMapping("/initiate/{bid_id}")
    public ApiResponse<?> initiatePayment(@PathVariable("bid_id") Long bidId) throws Exception {
        return new ApiResponse<>(HTTPStatus.ACCEPTED, paymentService.initiatePayment(bidId));
    }

    @PostMapping("/success")
    public ApiResponse<?> success(@RequestParam Map<String, String> validatorResponse) {
        log.info("Success Response: {}", validatorResponse);
        return new ApiResponse<>(HTTPStatus.ACCEPTED, validatorResponse);
    }

    @PostMapping("/fail")
    public ApiResponse<?> fail(@RequestParam Map<String, String> validatorResponse) {
        log.info("Fail Response: {}", validatorResponse);
        return new ApiResponse<>(HTTPStatus.BAD_REQUEST, validatorResponse);
    }

    @PostMapping("/cancel")
    public ApiResponse<?> cancel(@RequestParam Map<String, String> validatorResponse) {
        log.info("Cancel Response: {}", validatorResponse);
        return new ApiResponse<>(HTTPStatus.BAD_REQUEST, validatorResponse);
    }

    @PostMapping("/ipn")
    public ApiResponse<?> handleIpn(@RequestParam Map<String, String> ipnResponse, HttpServletRequest request) {
        paymentService.verifyTransaction(ipnResponse, request);
        return new ApiResponse<>(HTTPStatus.OK, null);
    }

}
