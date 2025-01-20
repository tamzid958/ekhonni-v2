package com.ekhonni.backend.controller;

import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.RefundService;
import com.ekhonni.backend.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Asif Iqbal
 * Date: 1/20/25
 */

@RestController
@RequestMapping("api/v2/refund")
@AllArgsConstructor
public class RefundController {

    RefundService refundService;
    TransactionService transactionService;


    /**
     * ===============================================
     *                 User api
     * ===============================================
     */

    @PostMapping("/{transaction_id}")
    public ApiResponse<?> create(@RequestParam("transaction_id") Long transactionId) {
        return new ApiResponse<>(HTTPStatus.CREATED, refundService.create(transactionId));
    }

}
