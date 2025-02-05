package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.refund.RefundApproveDTO;
import com.ekhonni.backend.dto.refund.RefundRequestDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.RefundService;
import com.ekhonni.backend.service.TransactionService;
import com.ekhonni.backend.util.ResponseUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: Asif Iqbal
 * Date: 1/20/25
 */

@RestController
@RequestMapping("api/v2/refund")
@AllArgsConstructor
@Tag(name = "Refund", description = "Manage refund operations")
public class RefundController {

    private final RefundService refundService;
    private final TransactionService transactionService;

    /**
     * ===============================================
     *                   User Api
     * ===============================================
     */

    @PostMapping("/{transaction_id}")
    @PreAuthorize("@transactionService.getBuyerId(transactionId) == authentication.principal.id")
    public ResponseEntity<ApiResponse<Void>> create(
            @PathVariable("transaction_id") Long transactionId,
            @Valid @RequestBody RefundRequestDTO refundRequestDTO) {
        refundService.create(transactionId, refundRequestDTO);
        return ResponseUtil.createResponse(HTTPStatus.CREATED);
    }


    /**
     * ===============================================
     *                   Admin Api
     * ===============================================
     */

    @PatchMapping("/approve/{id}")
    public ResponseEntity<ApiResponse<Void>> approve(@PathVariable Long id, @Valid @RequestBody RefundApproveDTO refundApproveDTO) {
        refundService.approve(id, refundApproveDTO);
        return ResponseUtil.createResponse(HTTPStatus.CREATED);
    }

    @PatchMapping("/reject/{id}")
    public ResponseEntity<ApiResponse<Void>> reject(@PathVariable Long id) {
        refundService.softDelete(id);
        return ResponseUtil.createResponse(HTTPStatus.CREATED);
    }

    @PatchMapping("/reject")
    public ResponseEntity<ApiResponse<Void>> reject(@RequestBody List<Long> ids) {
        refundService.softDelete(ids);
        return ResponseUtil.createResponse(HTTPStatus.CREATED);
    }


}
