package com.ekhonni.backend.repository;

import com.ekhonni.backend.enums.TransactionStatus;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: Asif Iqbal
 * Date: 12/19/24
 */
public interface TransactionRepository extends BaseRepository<Transaction, Long> {
    Optional<Bid> findBidById(Long bidId);
    Optional<Transaction> findByBidId(Long bidId);

    void deleteByBidId(Long bidId);

    boolean existsByBidIdAndDeletedAtIsNull(Long bidId);

    <P> Page<P> findByBidBidderIdAndDeletedAtIsNull(UUID bidderId, Class<P> projection, Pageable pageable);

    <P> Page<P> findByBidProductSellerIdAndDeletedAtIsNull(UUID sellerId, Class<P> projection, Pageable pageable);

    <P> Page<P> findByDeletedAtIsNullAndBidBidderIdOrBidProductSellerId(
            UUID bidderId, UUID sellerId,
            Class<P> projection, Pageable pageable
    );

    <P> Page<P> findByStatusAndDeletedAtIsNull(TransactionStatus status, Class<P> projection, Pageable pageable);

    <P> Page<P> findByBidBidderIdAndStatusAndDeletedAtIsNull(UUID userId, TransactionStatus status, Class<P> projection, Pageable pageable);

    <P> Page<P> findByCreatedAtBetweenAndDeletedAtIsNull(LocalDateTime startDate, LocalDateTime endDate, Class<P> projection, Pageable pageable);

    Page<Transaction> findByStatusEqualsAndUpdatedAtLessThanEqualAndDeletedAtIsNull(TransactionStatus status, LocalDateTime timestamp, Pageable pageable);

}
