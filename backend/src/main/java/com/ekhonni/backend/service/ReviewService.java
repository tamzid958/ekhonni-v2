package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.review.ReviewCreateDTO;
import com.ekhonni.backend.dto.review.ReviewUpdateDTO;
import com.ekhonni.backend.enums.BidStatus;
import com.ekhonni.backend.enums.ReviewType;
import com.ekhonni.backend.exception.bid.BidNotAcceptedException;
import com.ekhonni.backend.exception.bid.BidNotFoundException;
import com.ekhonni.backend.exception.review.ReviewNotFoundException;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.Review;
import com.ekhonni.backend.projection.review.BuyerReviewProjection;
import com.ekhonni.backend.projection.review.SellerReviewProjection;
import com.ekhonni.backend.repository.ReviewRepository;
import com.ekhonni.backend.util.AuthUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.UUID;

/**
 * Author: Asif Iqbal
 * Date: 1/27/25
 */

@Service
public class ReviewService extends BaseService<Review, Long> {

    private final ReviewRepository reviewRepository;
    private final BidService bidService;
    private final NotificationService notificationService;

    public ReviewService(ReviewRepository reviewRepository, BidService bidService, NotificationService notificationService) {
        super(reviewRepository);
        this.reviewRepository = reviewRepository;
        this.bidService = bidService;
        this.notificationService = notificationService;
    }

    @Transactional
    public void createSellerReview(ReviewCreateDTO dto) {
        handlePreviousReview(dto.bidId(), ReviewType.SELLER);
        Bid bid = bidService.get(dto.bidId()).orElseThrow(() -> new BidNotFoundException("Bid not found"));
        if (!EnumSet.of(BidStatus.ACCEPTED, BidStatus.PAID).contains(bid.getStatus())) {
            throw new BidNotAcceptedException("Bid not accepted");
        }
        Review review = new Review(bid, ReviewType.SELLER, dto.rating(), dto.description());
        reviewRepository.save(review);
        notificationService.createForSellerReview(bid);
    }

    @Transactional
    public void createBuyerReview(ReviewCreateDTO dto) {
        handlePreviousReview(dto.bidId(), ReviewType.BUYER);
        Bid bid = bidService.get(dto.bidId()).orElseThrow(() -> new BidNotFoundException("Bid not found"));
        if (!EnumSet.of(BidStatus.ACCEPTED, BidStatus.PAID).contains(bid.getStatus())) {
            throw new BidNotAcceptedException("Bid not accepted");
        }
        Review review = new Review(bid, ReviewType.BUYER, dto.rating(), dto.description());
        reviewRepository.save(review);
        notificationService.createForBuyerReview(bid);
    }

    @Transactional
    private void handlePreviousReview(Long bidId, ReviewType type) {
        reviewRepository.findFirstByBidIdAndTypeAndDeletedAtIsNull(bidId, type)
                .ifPresent(previousReview -> softDelete(previousReview.getId()));
    }

    @Transactional
    public void updateSellerReview(Long id, ReviewUpdateDTO dto) {
        Review previousReview = get(id).orElseThrow(() -> new ReviewNotFoundException("Review not found"));
        Review review = new Review(previousReview.getBid(), ReviewType.SELLER, dto.rating(), dto.description());
        softDelete(id);
        reviewRepository.save(review);
    }

    @Transactional
    public void updateBuyerReview(Long id, ReviewUpdateDTO dto) {
        Review previousReview = get(id).orElseThrow(() -> new ReviewNotFoundException("Review not found"));
        Review review = new Review(previousReview.getBid(), ReviewType.BUYER, dto.rating(), dto.description());
        softDelete(id);
        reviewRepository.save(review);
    }

    public SellerReviewProjection getSellerReview(Long productId) {
        return reviewRepository.findFirstByBidProductIdAndTypeAndDeletedAtIsNull(
                productId, ReviewType.SELLER, SellerReviewProjection.class);
    }

    public BuyerReviewProjection getBuyerReview(Long productId) {
        return reviewRepository.findFirstByBidProductIdAndTypeAndDeletedAtIsNull(
                productId, ReviewType.BUYER, BuyerReviewProjection.class);
    }

    public Page<SellerReviewProjection> getSellerReviews(UUID sellerId, Pageable pageable) {
        return reviewRepository.findByTypeAndBidProductSellerIdAndDeletedAtIsNull(ReviewType.SELLER, sellerId,
                SellerReviewProjection.class, pageable);
    }

    public Page<BuyerReviewProjection> getBuyerReviews(UUID buyerId, Pageable pageable) {
        return reviewRepository.findByTypeAndBidBidderIdAndDeletedAtIsNull(ReviewType.BUYER, buyerId,
                BuyerReviewProjection.class, pageable);
    }

    public Page<SellerReviewProjection> getUserSellerReviews(Pageable pageable) {
        return reviewRepository.findByTypeAndBidBidderIdAndDeletedAtIsNull(
                ReviewType.SELLER, AuthUtil.getAuthenticatedUser().getId(),
                SellerReviewProjection.class, pageable);
    }

    public Page<BuyerReviewProjection> getUserBuyerReviews(Pageable pageable) {
        return reviewRepository.findByTypeAndBidProductSellerIdAndDeletedAtIsNull(
                ReviewType.BUYER, AuthUtil.getAuthenticatedUser().getId(),
                BuyerReviewProjection.class, pageable);
    }

    public Object getReview(Long id) {
        Review review = get(id).orElseThrow(() -> new ReviewNotFoundException("Review not found"));
        if (ReviewType.SELLER.equals(review.getType())) {
            return get(id, SellerReviewProjection.class);
        } else {
            return get(id, BuyerReviewProjection.class);
        }
    }

    public Page<SellerReviewProjection> getEditHistoryForSellerReviews(Long productId, Pageable pageable) {
        return reviewRepository.findByBidProductIdAndType(productId, ReviewType.SELLER, SellerReviewProjection.class, pageable);
    }

    public Page<BuyerReviewProjection> getEditHistoryForBuyerReviews(Long productId, Pageable pageable) {
        return reviewRepository.findByBidProductIdAndType(productId, ReviewType.BUYER, BuyerReviewProjection.class, pageable);
    }

    public boolean isSellerReview(Long id) {
        Review review = get(id).orElseThrow(() -> new ReviewNotFoundException("Review not found"));
        return review.getType().equals(ReviewType.SELLER);
    }

    public boolean isBuyerReview(Long id) {
        Review review = get(id).orElseThrow(() -> new ReviewNotFoundException("Review not found"));
        return review.getType().equals(ReviewType.BUYER);
    }

    public UUID getBuyerId(Long id) {
        Review review = get(id).orElseThrow(() -> new ReviewNotFoundException("Review not found"));
        return review.getBid().getBidder().getId();
    }

    public UUID getSellerId(Long id) {
        Review review = get(id).orElseThrow(() -> new ReviewNotFoundException("Review not found"));
        return review.getBid().getProduct().getSeller().getId();
    }

    public Double getAverageSellerRating(UUID sellerId) {
        return reviewRepository.findAverageSellerRating(sellerId);
    }

    public Double getAverageBuyerRating(UUID buyerId) {
        return reviewRepository.findAverageBuyerRating(buyerId);
    }
}
