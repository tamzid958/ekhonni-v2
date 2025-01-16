package com.ekhonni.backend.controller;


import com.ekhonni.backend.dto.bid.BidCreateDTO;
import com.ekhonni.backend.enums.BidStatus;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.projection.bid.BidderBidProjection;
import com.ekhonni.backend.projection.bid.BuyerBidProjection;
import com.ekhonni.backend.projection.bid.SellerBidProjection;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.BidService;
import com.ekhonni.backend.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/bid")
@AllArgsConstructor
@Tag(name = "Bid", description = "Manage bid operations")
public class BidController {

    BidService bidService;
    ProductService productService;

    /**
     * ===============================================
     *            Bidder, Buyer, Public Api
     * ===============================================
     */

    @GetMapping("/{id}")
    @PreAuthorize("@bidService.getBidderId(#id) == authentication.principal.id")
    public ApiResponse<?> get(@PathVariable Long id) {
        return new ApiResponse<>(HTTPStatus.ACCEPTED, bidService.get(id, BidderBidProjection.class)) ;
    }

    @GetMapping("/buyer/{product_id}")
    public ApiResponse<?> getAllForProductBuyer(@PathVariable("product_id") Long productId, Pageable pageable) {
        return new ApiResponse<>(HTTPStatus.ACCEPTED,
                bidService.getAllBidsForProduct(productId, BuyerBidProjection.class, pageable));
    }

    @GetMapping("/{product_id}/count")
    public ApiResponse<?> getCountForProduct(@PathVariable("product_id") Long productId) {
        return new ApiResponse<>(HTTPStatus.ACCEPTED, bidService.getCountForProduct(productId));
    }

    @PostMapping()
    public ApiResponse<?> create(@Valid @RequestBody BidCreateDTO bidCreateDTO) {
        return new ApiResponse<>(HTTPStatus.CREATED, bidService.create(bidCreateDTO));
    }

    @PatchMapping("/{id}/update")
    @PreAuthorize("@bidService.getBidderId(#id) == authentication.principal.id")
    public ApiResponse<?> update(@PathVariable Long id, @Valid @RequestBody BidCreateDTO bidCreateDTO) {
        return new ApiResponse<>(HTTPStatus.ACCEPTED, bidService.updateBid(id, bidCreateDTO));
    }

    /**
     * ===============================================
     *                  Seller Api
     * ===============================================
     */

    @GetMapping("/seller/{product_id}")
    @PreAuthorize("@productService.getSellerId(#productId) == authentication.principal.id")
    public ApiResponse<?> getAllForProductSeller(@PathVariable("product_id") Long productId, Pageable pageable) {
        return new ApiResponse<>(HTTPStatus.ACCEPTED,
                bidService.getAllBidsForProduct(productId, SellerBidProjection.class, pageable));
    }

    @PatchMapping("/{id}/accept")
    @PreAuthorize("@bidService.isProductOwner(authentication.principal.id, #id)")
    public ApiResponse<?> accept(@PathVariable Long id) {
        bidService.accept(id);
        return new ApiResponse<>(HTTPStatus.ACCEPTED, null);
    }

    /**
     * ===============================================
     *                   Admin Api
     * ===============================================
     */

    @GetMapping
    public ApiResponse<?> getAll(Pageable pageable) {
        return new ApiResponse<>(HTTPStatus.ACCEPTED, bidService.getAll(SellerBidProjection.class, pageable));
    }

    @GetMapping("/{product_id}")
    public ApiResponse<?> getAllForProduct(@PathVariable("product_id") Long productId, Pageable pageable) {
        return new ApiResponse<>(HTTPStatus.ACCEPTED,
                bidService.getAllBidsForProduct(productId, SellerBidProjection.class, pageable));
    }

    @GetMapping("/{product_id}/audit")
    public ApiResponse<?> getAuditForProduct(@PathVariable("product_id") Long productId, Pageable pageable) {
        return new ApiResponse<>(HTTPStatus.ACCEPTED,
                bidService.getAuditForProduct(productId, SellerBidProjection.class, pageable));
    }

    @GetMapping("/{product_id}/audit-count")
    public ApiResponse<?> getAuditCountForProduct(@PathVariable("product_id") Long productId) {
        return new ApiResponse<>(HTTPStatus.ACCEPTED, bidService.getAuditCountForProduct(productId));
    }

    @PatchMapping("/{id}/update-status")
    public ApiResponse<?> updateStatus(@PathVariable Long id, @Valid @RequestBody BidStatus status) {
        bidService.updateStatus(id, status);
        return new ApiResponse<>(HTTPStatus.ACCEPTED, null);
    }

    @PatchMapping("/{id}/restore")
    public ApiResponse<?> restore(@PathVariable Long id) {
        bidService.restore(id);
        return new ApiResponse<>(HTTPStatus.ACCEPTED, null);
    }

    @PatchMapping("/restore")
    public ApiResponse<?> restore(@RequestBody List<Long> ids) {
        bidService.restore(ids);
        return new ApiResponse<>(HTTPStatus.ACCEPTED, null);
    }

    @PatchMapping("/restore-all")
    public ApiResponse<?> restoreAll() {
        bidService.restoreAll();
        return new ApiResponse<>(HTTPStatus.ACCEPTED, null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable Long id) {
        bidService.softDelete(id);
        return new ApiResponse<>(HTTPStatus.DELETED, null);
    }

    @DeleteMapping("/{id}/delete-permanently")
    public ApiResponse<?> deletePermanently(@PathVariable Long id) {
        bidService.deletePermanently(id);
        return new ApiResponse<>(HTTPStatus.DELETED, null);
    }

}
