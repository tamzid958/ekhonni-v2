package com.ekhonni.backend.controller;

import com.ekhonni.backend.config.payment.SSLCommerzConfig;
import com.ekhonni.backend.dto.cashin.CashInRequest;
import com.ekhonni.backend.dto.payment.PaymentRequest;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.enums.PaymentMethod;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.BidService;
import com.ekhonni.backend.service.payment.PaymentService;
import com.ekhonni.backend.service.payment.provider.sslcommrez.SSLCommerzApiClient;
import com.ekhonni.backend.service.payment.provider.sslcommrez.response.InitiatePaymentResponse;
import com.ekhonni.backend.util.ResponseUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * Author: Asif Iqbal
 * Date: 12/15/24
 */

@RestController
@RequestMapping("/api/v2/payment")
@AllArgsConstructor
@Slf4j
@Tag(name = "Payment", description = "Manage payment operations")
public class PaymentController {

    private final PaymentService paymentService;
    private final SSLCommerzApiClient sslCommerzApiClient;
    private final BidService bidService;
    private final SSLCommerzConfig sslCommerzConfig;

    @PostMapping("/initiate")
    @PreAuthorize("@bidService.getBidderId(#paymentRequest.bidId) == authentication.principal.id")
    public ResponseEntity<ApiResponse<InitiatePaymentResponse>> initiatePayment(@Valid @RequestBody PaymentRequest paymentRequest) throws Exception {
        return ResponseUtil.createResponse(HTTPStatus.OK, paymentService.processPayment(paymentRequest));
    }

    @PostMapping("/cash-in")
    public ResponseEntity<ApiResponse<InitiatePaymentResponse>> initiateCashIn(
            @Valid @RequestBody CashInRequest cashInRequest) throws Exception {
        return ResponseUtil.createResponse(HTTPStatus.OK, paymentService.processCashIn(cashInRequest));
    }

    @GetMapping("/methods")
    public ResponseEntity<?> getAllPaymentMethods() {
        return ResponseUtil.createResponse(HTTPStatus.OK, Arrays.asList(PaymentMethod.values()));
    }

    @PostMapping("/sslcommerz/success")
    public void success(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Payment successful");
        response.sendRedirect(sslCommerzConfig.getSuccessRedirectUrl());
    }

    @PostMapping("/sslcommerz/fail")
    public void fail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Payment failed");
        response.sendRedirect(sslCommerzConfig.getFailRedirectUrl());
    }

    @PostMapping("/sslcommerz/cancel")
    public void cancel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Payment canceled");
        response.sendRedirect(sslCommerzConfig.getCancelRedirectUrl());
    }

    @PostMapping("/sslcommerz/ipn")
    public ResponseEntity<?> handlePaymentIpn(
            @NotNull @RequestParam Map<String, String> ipnResponse, @NotNull HttpServletRequest request) {
        sslCommerzApiClient.verifyTransaction(ipnResponse, request);
        return ResponseUtil.createResponse(HTTPStatus.OK);
    }

    @PostMapping("/cash-in/sslcommerz/ipn")
    public ResponseEntity<?> handleCashInIpn(
            @NotNull @RequestParam Map<String, String> ipnResponse, @NotNull HttpServletRequest request) {
        sslCommerzApiClient.verifyCashIn(ipnResponse, request);
        return ResponseUtil.createResponse(HTTPStatus.OK);
    }

}
