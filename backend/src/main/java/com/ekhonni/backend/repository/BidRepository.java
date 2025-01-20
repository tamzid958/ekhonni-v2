package com.ekhonni.backend.repository;

import com.ekhonni.backend.enums.BidStatus;
import com.ekhonni.backend.model.Bid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BidRepository extends BaseRepository<Bid, Long> {

    <P> List<P> findByProductIdAndDeletedAtIsNull(Long productId, Class<P> projection);

    <P> Page<P> findByProductIdAndDeletedAtIsNull(Long productId, Class<P> projection, Pageable pageable);

    <P> Page<P> findByProductId(Long productId, Class<P> projection, Pageable pageable);

    <P> Page<P> findAllByBidderId(Long bidderId, Class<P> projection, Pageable pageable);

    @Query("SELECT b.bidder.id FROM Bid b WHERE b.id = :id")
    Optional<UUID> findBidderIdById(Long id);

    boolean existsByProductIdAndStatus(Long productId, BidStatus status);

    long countByProductIdAndDeletedAtIsNull(Long productId);

    long countByProductId(Long productId);
}