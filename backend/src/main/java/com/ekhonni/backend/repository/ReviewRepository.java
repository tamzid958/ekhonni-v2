package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Author: Md Sakil Ahmed
 * Date: 06/01/25
 */

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBuyerId(UUID buyerId);
    List<Review> findBySellerId(UUID sellerId);
}
