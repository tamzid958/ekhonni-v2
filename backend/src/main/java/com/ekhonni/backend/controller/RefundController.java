package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.refund.RefundApproveDTO;
import com.ekhonni.backend.dto.refund.RefundRequestDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.RefundService;
import com.ekhonni.backend.service.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Asif Iqbal
 * Date: 1/20/25
 */

@RestController
@RequestMapping("api/v2/refund")
@AllArgsConstructor
@Tag(name = "Refund", description = "Manage refund operations")
public class RefundController {

    RefundService refundService;
    TransactionService transactionService;

    /**
     * ===============================================
     *                   User Api
     * ===============================================
     */

    @PostMapping("/{transaction_id}")
    @PreAuthorize("@transactionService.getBuyerId(transactionId) == authentication.principal.id")
    public ApiResponse<?> create(
            @PathVariable("transaction_id") Long transactionId,
            @Valid @RequestBody RefundRequestDTO refundRequestDTO) {
        refundService.create(transactionId, refundRequestDTO);
        return new ApiResponse<>(HTTPStatus.CREATED, null);
    }


    /**
     * ===============================================
     *                   Admin Api
     * ===============================================
     */

    @PatchMapping("/approve/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ApiResponse<?> approve(@PathVariable Long id, @Valid @RequestBody RefundApproveDTO refundApproveDTO) {
        refundService.approve(id, refundApproveDTO);
        return new ApiResponse<>(HTTPStatus.CREATED, null);
    }


}
