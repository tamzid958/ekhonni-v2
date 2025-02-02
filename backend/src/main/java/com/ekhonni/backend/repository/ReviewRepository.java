package com.ekhonni.backend.repository;

import com.ekhonni.backend.enums.ReviewType;
import com.ekhonni.backend.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Author: Asif Iqbal
 * Date: 1/27/25
 */

@Repository
public interface ReviewRepository extends BaseRepository<Review, Long> {

    Optional<Review> findFirstByBidIdAndTypeAndDeletedAtIsNull(Long bidId, ReviewType type);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.bid.product.seller.id = :sellerId AND r.type = 'SELLER' AND r.deletedAt IS NULL")
    Double findAverageSellerRating(UUID sellerId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.bid.bidder.id = :bidderId AND r.type = 'BUYER' AND r.deletedAt IS NULL")
    Double findAverageBuyerRating(UUID bidderId);

    <P> P findFirstByBidProductIdAndTypeAndDeletedAtIsNull(Long productId, ReviewType type, Class<P> projection);

    <P> Page<P> findByTypeAndBidProductSellerIdAndDeletedAtIsNull(
            ReviewType type, UUID sellerId,
            Class<P> projection, Pageable pageable);

    <P> Page<P> findByTypeAndBidBidderIdAndDeletedAtIsNull(
            ReviewType type, UUID bidderId,
            Class<P> projection, Pageable pageable);

    <P> Page<P> findByBidProductIdAndType(Long productId, ReviewType type, Class<P> projection, Pageable pageable);

}
