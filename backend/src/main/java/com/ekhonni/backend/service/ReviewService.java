package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.ReviewDTO;
import com.ekhonni.backend.model.Review;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.repository.ReviewRepository;
import com.ekhonni.backend.repository.UserRepository;
import com.ekhonni.backend.repository.BidRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Author: Md Sakil Ahmed
 * Date: 06/01/25
 */

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BidRepository bidRepository;

    private ReviewDTO convertToDTO(Review review) {
        return new ReviewDTO(
                review.getBuyer() != null ? review.getBuyer().getId() : null,
                review.getSeller() != null ? review.getSeller().getId() : null,
                review.getBid() != null ? review.getBid().getId() : null,
                review.getRating(),
                review.getComment()
        );
    }

    private Review convertToEntity(ReviewDTO reviewDTO) throws Exception {
        Review review = new Review();
        review.setRating(reviewDTO.rating());
        review.setComment(reviewDTO.comment());
        review.setIsDeleted(false);
        Optional<User> buyer = userRepository.findById(reviewDTO.buyerId());
        Optional<User> seller = userRepository.findById(reviewDTO.sellerId());
        Optional<Bid> bid = bidRepository.findById(reviewDTO.bidId());
        if (buyer.isPresent()) review.setBuyer(buyer.get());
        else throw new Exception("Buyer not found");
        if (seller.isPresent()) review.setSeller(seller.get());
        else throw new Exception("Seller not found");
        if (bid.isPresent()) review.setBid(bid.get());
        else throw new Exception("Bid not found");

        return review;
    }

    public ReviewDTO saveReview(ReviewDTO reviewDTO) throws Exception {
        Review review = convertToEntity(reviewDTO);
        Review savedReview = reviewRepository.save(review);
        return convertToDTO(savedReview);
    }

    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ReviewDTO> getReviewById(Long id) {
        Optional<Review> review = reviewRepository.findById(id);
        return review.map(this::convertToDTO);
    }


    public List<ReviewDTO> getReviewsByBuyerId(UUID buyerId) {
        List<Review> reviews = reviewRepository.findByBuyerId(buyerId);
        if (reviews == null || reviews.isEmpty()) {
            throw new EntityNotFoundException("No reviews found for the buyer with ID: " + buyerId);
        }

        return reviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public boolean deleteReview(Long id) {
        Optional<Review> reviewOpt = reviewRepository.findById(id);
        if (reviewOpt.isPresent()) {
            Review review = reviewOpt.get();
            review.setIsDeleted(true);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    public boolean permanentlyDeleteReview(Long id) {
        Optional<Review> reviewOpt = reviewRepository.findById(id);
        if (reviewOpt.isPresent()) {
            reviewRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<ReviewDTO> getReviewsBySellerId(UUID sellerId) {
        List<Review> reviews = reviewRepository.findBySellerId(sellerId);
        if (reviews == null || reviews.isEmpty()) {
            throw new EntityNotFoundException("No reviews found for the seller with ID: " + sellerId);
        }
        return reviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
