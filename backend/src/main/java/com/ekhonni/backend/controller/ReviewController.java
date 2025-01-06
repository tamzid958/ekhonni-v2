package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.ReviewDTO;
import com.ekhonni.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: Md Sakil Ahmed
 * Date: 06/01/25
 */

@RestController
@RequestMapping("/api/v2/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/create-review")
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO review) throws Exception {
        try{
            ReviewDTO savedReview = reviewService.saveReview(review);
            return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);        }
    }

    @GetMapping("/getAllReviews")
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/getReviewById/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id) {
        Optional<ReviewDTO> review = reviewService.getReviewById(id);
        return review.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getBuyerReviewById/{id}")
    public ResponseEntity<List<ReviewDTO>> getBuyerReviewsById(@PathVariable UUID id){
        try {
            List<ReviewDTO> reviews = reviewService.getReviewsByBuyerId(id);
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getSellerReviewsById/{id}")
    public ResponseEntity<List<ReviewDTO>> getSellerReviewsById(@PathVariable UUID id) {
        try {
            List<ReviewDTO> reviews = reviewService.getReviewsBySellerId(id);
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/sofDeleteReviewById/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        boolean deleted = reviewService.deleteReview(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteReviewById/{id}")
    public ResponseEntity<Void> permanentlyDeleteReview(@PathVariable Long id) {
        boolean deleted = reviewService.permanentlyDeleteReview(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
