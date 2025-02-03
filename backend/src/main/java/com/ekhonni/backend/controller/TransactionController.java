package com.ekhonni.backend.controller;

import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.enums.TransactionStatus;
import com.ekhonni.backend.projection.transaction.TransactionProjection;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.TransactionService;
import com.ekhonni.backend.util.ResponseUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Author: Asif Iqbal
 * Date: 2/3/25
 */
@RestController
@RequestMapping("/api/v2/transaction")
@AllArgsConstructor
@Tag(name = "Transaction", description = "Manage read operations of product payment")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TransactionProjection>> get(@PathVariable Long id) {
        return ResponseUtil.createResponse(HTTPStatus.OK, transactionService.get(id, TransactionProjection.class));
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<Page<TransactionProjection>>> getAllForUser(Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, transactionService.getAllForUser(pageable));
    }

    @GetMapping("/user/status/{status}")
    public ResponseEntity<ApiResponse<Page<TransactionProjection>>> getUserTransactionsByStatus(
            @PathVariable TransactionStatus status, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, transactionService.getUserTransactionsByStatus(status, pageable));
    }

    /**
     * ==========================================================
     *                    Admin API
     * ==========================================================
     */

    @GetMapping("/user/{user_id}")
    public ResponseEntity<ApiResponse<Page<TransactionProjection>>> getAllForUserAdmin(
            @PathVariable("user_id") UUID userId, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, transactionService.getAllForUserAdmin(userId, pageable));
    }

    @GetMapping("/user/{user_id}/status/{status}")
    public ResponseEntity<ApiResponse<Page<TransactionProjection>>> getUserTransactionsByStatusAdmin(
            @PathVariable("user_id") UUID userId, @PathVariable TransactionStatus status, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK,
                transactionService.getUserTransactionsByStatusAdmin(userId, status, pageable));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<TransactionProjection>>> getAllTransactions(Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, transactionService.getAllTransactions(pageable));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<Page<TransactionProjection>>> getAllByStatus(
            @PathVariable TransactionStatus status, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, transactionService.getAllByStatus(status, pageable));
    }

    @GetMapping("/date-range")
    public ResponseEntity<ApiResponse<Page<TransactionProjection>>> getTransactionsByDateRange(
            @RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK,
                transactionService.getTransactionsByDateRange(startDate, endDate, pageable));
    }
}
