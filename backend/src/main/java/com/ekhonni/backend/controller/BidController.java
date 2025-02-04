package com.ekhonni.backend.controller;


import com.ekhonni.backend.dto.bid.BidCreateDTO;
import com.ekhonni.backend.dto.bid.BidUpdateDTO;
import com.ekhonni.backend.enums.BidStatus;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.projection.bid.AdminBidProjection;
import com.ekhonni.backend.projection.bid.BidderBidProjection;
import com.ekhonni.backend.projection.bid.BuyerBidProjection;
import com.ekhonni.backend.projection.bid.SellerBidProjection;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.BidService;
import com.ekhonni.backend.service.ProductService;
import com.ekhonni.backend.util.ResponseUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2/bid")
@AllArgsConstructor
@Tag(name = "Bid", description = "Manage bid operations")
public class BidController {

    private final BidService bidService;
    private final ProductService productService;

    /**
     *================================================================
     *                   Public, Buyer, Seller API
     *================================================================
     */
    @PostMapping()
    public ResponseEntity<ApiResponse<Void>> create(@Valid @RequestBody BidCreateDTO bidCreateDTO) {
        bidService.handlePreviousBid(bidCreateDTO);
        bidService.create(bidCreateDTO);
        return ResponseUtil.createResponse(HTTPStatus.CREATED);
    }

    @PatchMapping("/{id}/update")
    @PreAuthorize("@bidService.getBidderId(#id) == authentication.principal.id")
    public ResponseEntity<ApiResponse<Void>> update(
            @PathVariable Long id, @Valid @RequestBody BidUpdateDTO bidUpdateDTO) {
        bidService.updateBid(id, bidUpdateDTO);
        return ResponseUtil.createResponse(HTTPStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/accept")
    @PreAuthorize("@bidService.isProductOwner(authentication.principal.id, #id)")
    public ResponseEntity<ApiResponse<Void>> accept(@PathVariable Long id) {
        bidService.accept(id);
        return ResponseUtil.createResponse(HTTPStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@bidService.getBidderId(#id) == authentication.principal.id")
    public ResponseEntity<ApiResponse<BidderBidProjection>> get(@PathVariable Long id) {
        return ResponseUtil.createResponse(HTTPStatus.OK, bidService.get(id, BidderBidProjection.class));
    }

    @GetMapping("/bidder")
    public ResponseEntity<ApiResponse<Page<BidderBidProjection>>> getAllForUser(Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, bidService.getAllForAuthenticatedUser(BidderBidProjection.class, pageable));
    }

    @GetMapping("/product/{product_id}/highest")
    public ResponseEntity<ApiResponse<Double>> getHighestForProduct(@PathVariable("product_id") Long productId) {
        return ResponseUtil.createResponse(HTTPStatus.OK, bidService.getHighestBidAmount(productId));
    }

    @GetMapping("/buyer/product/{product_id}")
    public ResponseEntity<ApiResponse<Page<BuyerBidProjection>>> getAllForProductBuyer(
            @PathVariable("product_id") Long productId, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK,
                bidService.getAllForProduct(productId, BuyerBidProjection.class, pageable));
    }

    @GetMapping("/user/product/{product_id}")
    public ResponseEntity<ApiResponse<BuyerBidProjection>> getAuthenticatedUserBidForProduct(@PathVariable("product_id") Long productId) {
        return ResponseUtil.createResponse(HTTPStatus.OK, bidService.getAuthenticatedUserBidForProduct(productId));
    }

    @GetMapping("/product/{product_id}/count")
    public ResponseEntity<ApiResponse<Long>> getCountForProduct(@PathVariable("product_id") Long productId) {
        return ResponseUtil.createResponse(HTTPStatus.OK, bidService.getCountForProduct(productId));
    }

    @GetMapping("/seller/product/{product_id}")
    @PreAuthorize("@productService.getSellerId(#productId) == authentication.principal.id")
    public ResponseEntity<ApiResponse<Page<SellerBidProjection>>> getAllForProductSeller(
            @PathVariable("product_id") Long productId, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK,
                bidService.getAllForProduct(productId, SellerBidProjection.class, pageable));
    }

    /**
     *================================================================
     *                          Admin API
     *================================================================
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<AdminBidProjection>>> getAll(Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, bidService.getAll(AdminBidProjection.class, pageable));
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<ApiResponse<Page<AdminBidProjection>>> getAllForUserAdmin(
            @PathVariable("user_id") UUID userId, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK,
                bidService.getAllForUser(userId, AdminBidProjection.class, pageable));
    }

    @GetMapping("/admin/product/{product_id}")
    public ResponseEntity<ApiResponse<Page<AdminBidProjection>>> getAllForProduct(
            @PathVariable("product_id") Long productId, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK,
                bidService.getAllForProduct(productId, AdminBidProjection.class, pageable));
    }

    @GetMapping("/product/{product_id}/audit")
    public ResponseEntity<ApiResponse<Page<AdminBidProjection>>> getAuditForProduct(
            @PathVariable("product_id") Long productId, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK,
                bidService.getAuditForProduct(productId, AdminBidProjection.class, pageable));
    }

    @GetMapping("/product/{product_id}/audit-count")
    public ResponseEntity<ApiResponse<Long>> getAuditCountForProduct(@PathVariable("product_id") Long productId) {
        return ResponseUtil.createResponse(HTTPStatus.OK, bidService.getAuditCountForProduct(productId));
    }

    @PatchMapping("/{id}/update-status")
    public ResponseEntity<ApiResponse<Void>> updateStatus(@PathVariable Long id, @Valid @RequestBody BidStatus status) {
        bidService.updateStatus(id, status);
        return ResponseUtil.createResponse(HTTPStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<Void>> restore(@PathVariable Long id) {
        bidService.restore(id);
        return ResponseUtil.createResponse(HTTPStatus.NO_CONTENT);
    }

    @PatchMapping("/restore")
    public ResponseEntity<ApiResponse<Void>> restore(@RequestBody List<Long> ids) {
        bidService.restore(ids);
        return ResponseUtil.createResponse(HTTPStatus.NO_CONTENT);
    }

    @PatchMapping("/restore-all")
    public ResponseEntity<ApiResponse<Void>> restoreAll() {
        bidService.restoreAll();
        return ResponseUtil.createResponse(HTTPStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        bidService.softDelete(id);
        return ResponseUtil.createResponse(HTTPStatus.DELETED);
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse<Void>> delete(@RequestBody List<Long> ids) {
        bidService.softDelete(ids);
        return ResponseUtil.createResponse(HTTPStatus.DELETED);
    }

    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<ApiResponse<Void>> deletePermanently(@PathVariable Long id) {
        bidService.deletePermanently(id);
        return ResponseUtil.createResponse(HTTPStatus.DELETED);
    }
}