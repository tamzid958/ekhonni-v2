package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.review.ReviewCreateDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.BidService;
import com.ekhonni.backend.service.ReviewService;
import com.ekhonni.backend.util.ResponseUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

//    @PatchMapping("/{id}")
//    public ResponseEntity<ApiResponse<Void>> updateReview(
//            @PathVariable Long id,
//            @Valid @RequestBody ReviewRequest reviewRequest) {
//        return ResponseEntity.ok(reviewService.updateReview(reviewId, reviewRequest));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ReviewResponse> getReviewById(
//            @PathVariable("id") Long id) {
//        return ResponseEntity.ok(reviewService.getReviewById(id));
//    }
//
//    @DeleteMapping("/{reviewId}")
//    public void deleteReview(@PathVariable Long reviewId) {
//        reviewService.deleteReview(reviewId);
//    }
//
//    @GetMapping("/about/{userId}")
//    @Operation(summary = "Get all reviews about a user (as buyer or seller)")
//    public ResponseEntity<List<ReviewResponse>> getReviewsAboutUser(
//            @PathVariable Long userId) {
//        return ResponseEntity.ok(reviewService.getReviewsAboutUser(userId));
//    }
//
//    @GetMapping("/by/{userId}")
//    public ResponseEntity<List<ReviewResponse>> getReviewsByUser(
//            @PathVariable Long userId) {
//        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
//    }
//
//    @GetMapping("/bid/{bidId}")
//    public ResponseEntity<List<ReviewResponse>> getReviewsForBid(
//            @PathVariable Long bidId) {
//        return ResponseEntity.ok(reviewService.getReviewsForBid(bidId));
//    }
}
