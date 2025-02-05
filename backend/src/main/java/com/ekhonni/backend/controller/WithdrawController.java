package com.ekhonni.backend.controller;

import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.enums.WithdrawStatus;
import com.ekhonni.backend.projection.withdraw.WithdrawProjection;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.WithdrawService;
import com.ekhonni.backend.util.ResponseUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2/withdraw")
@AllArgsConstructor
@Tag(name = "Withdraw", description = "Manage read operations of withdraw")
public class WithdrawController {

    private final WithdrawService withdrawService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WithdrawProjection>> get(@PathVariable Long id) {
        return ResponseUtil.createResponse(HTTPStatus.OK, withdrawService.get(id, WithdrawProjection.class));
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<Page<WithdrawProjection>>> getAllForUser(Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, withdrawService.getAllForUser(pageable));
    }

    @GetMapping("/user/status/{status}")
    public ResponseEntity<ApiResponse<Page<WithdrawProjection>>> getUserWithdrawsByStatus(
            @PathVariable WithdrawStatus status, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, withdrawService.getUserWithdrawsByStatus(status, pageable));
    }

    /**
     * ==========================================================
     *                    Admin API
     * ==========================================================
     */

    @GetMapping
    public ResponseEntity<ApiResponse<Page<WithdrawProjection>>> getAllWithdraws(Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, withdrawService.getAllWithdraws(pageable));
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<ApiResponse<Page<WithdrawProjection>>> getAllForUserAdmin(
            @PathVariable("user_id") UUID userId, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, withdrawService.getAllForUserAdmin(userId, pageable));
    }

    @GetMapping("/user/{user_id}/status/{status}")
    public ResponseEntity<ApiResponse<Page<WithdrawProjection>>> getUserTransactionsByStatusAdmin(
            @PathVariable("user_id") UUID userId, @PathVariable WithdrawStatus status, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK,
                withdrawService.getUserTransactionsByStatusAdmin(userId, status, pageable));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<Page<WithdrawProjection>>> getAllByStatus(
            @PathVariable WithdrawStatus status, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, withdrawService.getAllByStatus(status, pageable));
    }

    @GetMapping("/user/{userId}/statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getUserWithdrawStatistics(@PathVariable Long userId) {
        return ResponseUtil.createResponse(HTTPStatus.OK, withdrawService.getUserWithdrawStatistics(userId));
    }

    @GetMapping("/date-range")
    public ResponseEntity<ApiResponse<Page<WithdrawProjection>>> getWithdrawsByDateRange(
            @RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, withdrawService.getWithdrawsByDateRange(startDate, endDate, pageable));
    }

}
