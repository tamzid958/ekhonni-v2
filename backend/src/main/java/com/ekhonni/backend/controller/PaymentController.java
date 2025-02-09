package com.ekhonni.backend.controller;

import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.BidService;
import com.ekhonni.backend.service.PaymentService;
import com.ekhonni.backend.util.ResponseUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    PaymentService paymentService;
    BidService bidService;

    @PostMapping("/initiate/{bid_id}")
    @PreAuthorize("@bidService.getBidderId(#bidId) == authentication.principal.id")
    public ResponseEntity<?> initiatePayment(@PathVariable("bid_id") Long bidId) throws Exception {
        return ResponseUtil.createResponse(HTTPStatus.OK, paymentService.initiatePayment(bidId));
    }

    @PostMapping("/success")
    public ResponseEntity<?> success(@RequestParam Map<String, String> validatorResponse) {
        log.info("Success Response: {}", validatorResponse);
        return ResponseUtil.createResponse(HTTPStatus.OK, validatorResponse);
    }

    @PostMapping("/fail")
    public ResponseEntity<?> fail(@RequestParam Map<String, String> validatorResponse) {
        log.info("Fail Response: {}", validatorResponse);
        return ResponseUtil.createResponse(HTTPStatus.BAD_REQUEST, validatorResponse);
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancel(@RequestParam Map<String, String> validatorResponse) {
        log.info("Cancel Response: {}", validatorResponse);
        return ResponseUtil.createResponse(HTTPStatus.PAYMENT_REQUIRED, validatorResponse);
    }

    @PostMapping("/ipn")
    public ResponseEntity<?> handleIpn(@NotNull @RequestParam Map<String, String> ipnResponse,
                                       @NotNull HttpServletRequest request) {
        paymentService.verifyTransaction(ipnResponse, request);
        return ResponseUtil.createResponse(HTTPStatus.OK);
    }

}
