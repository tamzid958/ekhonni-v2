package com.ekhonni.backend.repository;

import com.ekhonni.backend.enums.BidStatus;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BidRepository extends BaseRepository<Bid, Long> {

    <P> Page<P> findByProductIdAndDeletedAtIsNull(Long productId, Class<P> projection, Pageable pageable);

    <P> Page<P> findByBidderIdAndDeletedAtIsNull(UUID bidderId, Class<P> projection, Pageable pageable);

    <P> Page<P> findByProductSellerIdAndDeletedAtIsNull(UUID id, Class<P> projection, Pageable pageable);

    <P> Page<P> findByBidderIdAndStatusAndDeletedAtIsNull(UUID bidderId, BidStatus status, Class<P> projection, Pageable pageable);

    <P> Page<P> findByProductSellerIdAndStatusAndDeletedAtIsNull(UUID id, BidStatus status, Class<P> projection, Pageable pageable);

    Optional<Bid> findByProductIdAndBidderIdAndDeletedAtIsNull(Long productId, UUID bidderId);

    <P> Optional<P> findByProductIdAndBidderIdAndDeletedAtIsNull(Long productId, UUID bidderId, Class<P> projection);

    @Query("SELECT b.bidder.id FROM Bid b WHERE b.id = :id AND b.deletedAt IS NULL")
    Optional<UUID> findBidderIdById(Long id);

    boolean existsByProductIdAndStatusAndDeletedAtIsNull(Long productId, BidStatus status);

    long countByProductIdAndDeletedAtIsNull(Long productId);

    Optional<Bid> findTopByProductIdAndDeletedAtIsNullOrderByAmountDesc(Long productId);

    <P> Page<P> findByProductId(Long productId, Class<P> projection, Pageable pageable);

    long countByProductId(Long productId);

    User findByProductIdAndStatusAndDeletedAtIsNull(Long productId, BidStatus status);

    User findByProductIdAndStatusInAndDeletedAtIsNull(Long productId, List<BidStatus> statuses);

}