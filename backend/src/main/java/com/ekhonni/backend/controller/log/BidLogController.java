package com.ekhonni.backend.controller.log;

import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.model.log.BidLog;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.log.BidLogService;
import com.ekhonni.backend.util.ResponseUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Author: Asif Iqbal
 * Date: 2/12/25
 */
@RestController
@RequestMapping("/api/v2/bid-log")
@AllArgsConstructor
@Tag(name = "BidLog", description = "Manage read operations of bid logs")
public class BidLogController {

    private final BidLogService bidLogService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BidLog>> getById(@PathVariable Long id) {
        BidLog bidLog = bidLogService.get(id).orElseThrow(() -> new RuntimeException("Bid log not found"));
        return ResponseUtil.createResponse(HTTPStatus.OK, bidLog);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<BidLog>>> getAll(Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, bidLogService.getAll(pageable));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<Page<BidLog>>> getByProductId(@PathVariable Long productId, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, bidLogService.getByProductId(productId, pageable));
    }

    @GetMapping("/bid/{bidId}")
    public ResponseEntity<ApiResponse<Page<BidLog>>> getByBidId(@PathVariable Long bidId, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, bidLogService.getByBidId(bidId, pageable));
    }

    @GetMapping("/date-range")
    public ResponseEntity<ApiResponse<Page<BidLog>>> getByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, bidLogService.getByDateRange(startDate, endDate, pageable));
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<Void>> restore(@PathVariable Long id) {
        bidLogService.restore(id);
        return ResponseUtil.createResponse(HTTPStatus.NO_CONTENT);
    }

    @PatchMapping("/restore")
    public ResponseEntity<ApiResponse<Void>> restore(@RequestBody List<Long> ids) {
        bidLogService.restore(ids);
        return ResponseUtil.createResponse(HTTPStatus.NO_CONTENT);
    }

    @PatchMapping("/restore-all")
    public ResponseEntity<ApiResponse<Void>> restoreAll() {
        bidLogService.restoreAll();
        return ResponseUtil.createResponse(HTTPStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        bidLogService.softDelete(id);
        return ResponseUtil.createResponse(HTTPStatus.DELETED);
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse<Void>> delete(@RequestBody List<Long> ids) {
        bidLogService.softDelete(ids);
        return ResponseUtil.createResponse(HTTPStatus.DELETED);
    }

    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<ApiResponse<Void>> deletePermanently(@PathVariable Long id) {
        bidLogService.deletePermanently(id);
        return ResponseUtil.createResponse(HTTPStatus.DELETED);
    }
}
