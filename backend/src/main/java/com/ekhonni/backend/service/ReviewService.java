package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.review.ReviewCreateDTO;
import com.ekhonni.backend.enums.BidStatus;
import com.ekhonni.backend.enums.ReviewType;
import com.ekhonni.backend.exception.bid.BidNotAcceptedException;
import com.ekhonni.backend.exception.bid.BidNotFoundException;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.Review;
import com.ekhonni.backend.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

/**
 * Author: Asif Iqbal
 * Date: 1/27/25
 */

@Service
public class ReviewService extends BaseService<Review, Long> {

    private final ReviewRepository reviewRepository;
    private final BidService bidService;

    public ReviewService(ReviewRepository reviewRepository, BidService bidService) {
        super(reviewRepository);
        this.reviewRepository = reviewRepository;
        this.bidService = bidService;
    }

    @Transactional
    public void createSellerReview(ReviewCreateDTO dto) {
        Bid bid = bidService.get(dto.bidId()).orElseThrow(() -> new BidNotFoundException("Bid not found"));
        if (!EnumSet.of(BidStatus.ACCEPTED, BidStatus.PAID).contains(bid.getStatus())) {
            throw new BidNotAcceptedException("Bid not accepted");
        }
        Review review = new Review(bid, ReviewType.SELLER, dto.rating(), dto.description());
        reviewRepository.save(review);
    }

    @Transactional
    public void createBuyerReview(ReviewCreateDTO dto) {
        Bid bid = bidService.get(dto.bidId()).orElseThrow(() -> new BidNotFoundException("Bid not found"));
        if (!EnumSet.of(BidStatus.ACCEPTED, BidStatus.PAID).contains(bid.getStatus())) {
            throw new BidNotAcceptedException("Bid not accepted");
        }
        Review review = new Review(bid, ReviewType.SELLER, dto.rating(), dto.description());
        reviewRepository.save(review);
    }




}
