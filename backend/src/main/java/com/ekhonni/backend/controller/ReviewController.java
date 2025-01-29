package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.review.ReviewCreateDTO;
import com.ekhonni.backend.dto.review.ReviewUpdateDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.projection.review.BuyerReviewProjection;
import com.ekhonni.backend.projection.review.SellerReviewProjection;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.BidService;
import com.ekhonni.backend.service.ReviewService;
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
import java.util.Objects;
import java.util.UUID;

/**
 * Author: Asif Iqbal
 * Date: 1/27/25
 */

@RestController
@RequestMapping("/api/v2/review")
@AllArgsConstructor
@Tag(name = "Review", description = "Manage review operations")
public class ReviewController {

    private final ReviewService reviewService;
    private final BidService bidService;

    @PostMapping("/seller")
    @PreAuthorize("@bidService.getBidderId(#reviewCreateDTO.bidId) == authentication.principal.id")
    public ResponseEntity<ApiResponse<Void>> createSellerReview(@Valid @RequestBody ReviewCreateDTO reviewCreateDTO) {
        reviewService.createSellerReview(reviewCreateDTO);
        return ResponseUtil.createResponse(HTTPStatus.CREATED);
    }

    @PostMapping("/buyer")
    @PreAuthorize("@bidService.isProductOwner(authentication.principal.id, #reviewCreateDTO.bidId)")
    public ResponseEntity<ApiResponse<Void>> createBuyerReview(@Valid @RequestBody ReviewCreateDTO reviewCreateDTO) {
        reviewService.createBuyerReview(reviewCreateDTO);
        return ResponseUtil.createResponse(HTTPStatus.CREATED);
    }

    @PatchMapping("/{id}/seller")
    @PreAuthorize("@reviewService.getBuyerId(#id) == authentication.principal.id && @reviewService.isSellerReview(#id)")
    public ResponseEntity<ApiResponse<Void>> updateSellerReview(@PathVariable Long id, @Valid @RequestBody ReviewUpdateDTO reviewUpdateDTO) {
        reviewService.updateSellerReview(id, reviewUpdateDTO);
        return ResponseUtil.createResponse(HTTPStatus.NO_CONTENT);
    }

    @PatchMapping("{id}/buyer")
    @PreAuthorize("@reviewService.getSellerId(#id) == authentication.principal.id && @reviewService.isBuyerReview(#id)")
    public ResponseEntity<ApiResponse<ReviewUpdateDTO>> updateBuyerReview(@PathVariable Long id, @Valid @RequestBody ReviewUpdateDTO reviewUpdateDTO) {
        reviewService.updateBuyerReview(id, reviewUpdateDTO);
        return ResponseUtil.createResponse(HTTPStatus.NO_CONTENT);
    }


    @GetMapping("/seller/{seller_id}")
    public ResponseEntity<ApiResponse<Page<SellerReviewProjection>>> getSellerReviews(
            @PathVariable("seller_id") UUID sellerId, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, reviewService.getSellerReviews(sellerId, pageable));
    }

    @GetMapping("/buyer/{buyer_id}")
    public ResponseEntity<ApiResponse<Page<BuyerReviewProjection>>> getBuyerReviews(
            @PathVariable("buyer_id") UUID buyerId, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, reviewService.getBuyerReviews(buyerId, pageable));
    }

    @GetMapping("/user/seller")
    public ResponseEntity<ApiResponse<Page<SellerReviewProjection>>> getUserSellerReviews(Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, reviewService.getUserSellerReviews(pageable));
    }

    @GetMapping("/user/buyer")
    public ResponseEntity<ApiResponse<Page<BuyerReviewProjection>>> getUserBuyerReviews(Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, reviewService.getUserBuyerReviews(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getReview(@PathVariable("id") Long id) {
        return ResponseUtil.createResponse(HTTPStatus.OK, reviewService.getReview(id));
    }


    /**
     * =================================================================
     *                           Admin API
     * =================================================================
     */

    @GetMapping("/product/{product_id}/seller/edit-history")
    public ResponseEntity<ApiResponse<Page<SellerReviewProjection>>> getEditHistoryForSellerReviews(
            @PathVariable("product_id") Long productId, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, reviewService.getEditHistoryForSellerReviews(productId, pageable));
    }

    @GetMapping("/product/{product_id}/buyer/edit-history")
    public ResponseEntity<ApiResponse<Page<BuyerReviewProjection>>> getEditHistoryForBuyerReviews(
            @PathVariable("product_id") Long productId, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, reviewService.getEditHistoryForBuyerReviews(productId, pageable));
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<Void>> restore(@PathVariable Long id) {
        reviewService.restore(id);
        return ResponseUtil.createResponse(HTTPStatus.NO_CONTENT);
    }

    @PatchMapping("/restore")
    public ResponseEntity<ApiResponse<Void>> restore(@RequestBody List<Long> ids) {
        reviewService.restore(ids);
        return ResponseUtil.createResponse(HTTPStatus.NO_CONTENT);
    }

    @PatchMapping("/restore-all")
    public ResponseEntity<ApiResponse<Void>> restoreAll() {
        reviewService.restoreAll();
        return ResponseUtil.createResponse(HTTPStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        reviewService.softDelete(id);
        return ResponseUtil.createResponse(HTTPStatus.DELETED);
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse<Void>> delete(@RequestBody List<Long> ids) {
        reviewService.softDelete(ids);
        return ResponseUtil.createResponse(HTTPStatus.DELETED);
    }

    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<ApiResponse<Void>> deletePermanently(@PathVariable Long id) {
        reviewService.deletePermanently(id);
        return ResponseUtil.createResponse(HTTPStatus.DELETED);
    }
}
