package com.ekhonni.backend.controller;

import com.ekhonni.backend.payment.sslcommerz.PaymentRequest;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Author: Asif Iqbal
 * Date: 12/15/24
 */

@RestController
@RequestMapping("/api/v2/payment")
public record PaymentController(PaymentService paymentService) {

    @PostMapping("/initiate/{buyer_id}/{product_id}")
    public ApiResponse<?> initiatePayment(@PathVariable("buyer_id") UUID buyerId, @PathVariable("product_id") Long productId) {
        return paymentService.initiatePayment(buyerId, productId);
    }

    @PostMapping("/success")
    public ApiResponse<?> paymentSuccess() {
        return new ApiResponse<>(true, "Success", "Payment successful.", HttpStatus.OK);
    }

    @PostMapping("/fail")
    public ApiResponse<?> paymentFail() {
        return new ApiResponse<>(false, "Fail", "Payment failed.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/cancel")
    public ApiResponse<?> paymentCancel() {
        return new ApiResponse<>(false, "Cancel", "Payment canceled.", HttpStatus.PAYMENT_REQUIRED);
    }

}
