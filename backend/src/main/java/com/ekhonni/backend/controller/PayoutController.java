package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.withdraw.WithdrawRequest;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.enums.PaymentMethod;
import com.ekhonni.backend.enums.PayoutCategory;
import com.ekhonni.backend.enums.PayoutMethod;
import com.ekhonni.backend.exception.payout.PayoutProcessingException;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.payout.PayoutService;
import com.ekhonni.backend.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: Asif Iqbal
 * Date: 2/5/25
 */
@RestController
@RequestMapping("/api/v2/payout")
@AllArgsConstructor
public class PayoutController {

    private final PayoutService payoutService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> initiatePayout(@Valid @RequestBody WithdrawRequest withdrawRequest) throws PayoutProcessingException {
        payoutService.processPayout(withdrawRequest);
        return ResponseUtil.createResponse(HTTPStatus.OK);
    }

    @GetMapping("/methods")
    public ResponseEntity<?> getAllPayoutMethods() {
        Map<PayoutCategory, List<PayoutMethod>> payoutMethodsMap = Arrays.stream(PayoutCategory.values())
                .collect(Collectors.toMap(category -> category, PayoutCategory::getPayoutMethods));

        return ResponseUtil.createResponse(HTTPStatus.OK, payoutMethodsMap);
    }

}
