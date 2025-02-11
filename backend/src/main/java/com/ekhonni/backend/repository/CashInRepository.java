package com.ekhonni.backend.repository;

import com.ekhonni.backend.enums.TransactionStatus;
import com.ekhonni.backend.model.CashIn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: Asif Iqbal
 * Date: 2/10/25
 */
public interface CashInRepository extends BaseRepository<CashIn, Long> {

    Optional<CashIn> findByAccountIdAndDeletedAtIsNull(Long accountId);

    boolean existsByAccountIdAndDeletedAtIsNull(Long accountId);

    <P> Page<P> findByAccountUserIdAndDeletedAtIsNull(UUID userId, Class<P> projection, Pageable pageable);

    <P> Page<P> findByStatusAndDeletedAtIsNull(TransactionStatus status, Class<P> projection, Pageable pageable);

    <P> Page<P> findByAccountUserIdAndStatusAndDeletedAtIsNull(
            UUID userId, TransactionStatus status, Class<P> projection, Pageable pageable);

    <P> Page<P> findByCreatedAtBetweenAndDeletedAtIsNull(
            LocalDateTime startDate, LocalDateTime endDate, Class<P> projection, Pageable pageable);

    <P> Page<P> findByStatusEqualsAndUpdatedAtLessThanEqualAndDeletedAtIsNull(
            TransactionStatus status, LocalDateTime timestamp, Class<P> projection, Pageable pageable);

}