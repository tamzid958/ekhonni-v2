package com.ekhonni.backend.controller;

import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.enums.TransactionStatus;
import com.ekhonni.backend.projection.cashin.CashInProjection;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.CashInService;
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
 * Date: 2/10/25
 */
@RestController
@RequestMapping("/api/v2/cash-in")
@AllArgsConstructor
@Tag(name = "Cash In", description = "Manage cash-in operations")
public class CashInController {

    private final CashInService cashInService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CashInProjection>> get(@PathVariable Long id) {
        return ResponseUtil.createResponse(HTTPStatus.OK, cashInService.get(id, CashInProjection.class));
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<Page<CashInProjection>>> getAllForUser(Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, cashInService.getAllForAuthenticatedUser(pageable));
    }

    @GetMapping("/user/status/{status}")
    public ResponseEntity<ApiResponse<Page<CashInProjection>>> getUserCashInsByStatus(
            @PathVariable TransactionStatus status, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, cashInService.getUserCashInsByStatus(status, pageable));
    }

    /**
     * ==========================================================
     *                    Admin API
     * ==========================================================
     */

    @GetMapping("/user/{user_id}")
    public ResponseEntity<ApiResponse<Page<CashInProjection>>> getAllForUserAdmin(
            @PathVariable("user_id") UUID userId, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, cashInService.getAllForUserAdmin(userId, pageable));
    }

    @GetMapping("/user/{user_id}/status/{status}")
    public ResponseEntity<ApiResponse<Page<CashInProjection>>> getUserCashInsByStatusAdmin(
            @PathVariable("user_id") UUID userId, @PathVariable TransactionStatus status, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK,
                cashInService.getUserCashInsByStatusAdmin(userId, status, pageable));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CashInProjection>>> getAllCashIns(Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, cashInService.getAllCashIns(pageable));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<Page<CashInProjection>>> getAllByStatus(
            @PathVariable TransactionStatus status, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, cashInService.getAllByStatus(status, pageable));
    }

    @GetMapping("/date-range")
    public ResponseEntity<ApiResponse<Page<CashInProjection>>> getCashInsByDateRange(
            @RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK,
                cashInService.getCashInsByDateRange(startDate, endDate, pageable));
    }
}