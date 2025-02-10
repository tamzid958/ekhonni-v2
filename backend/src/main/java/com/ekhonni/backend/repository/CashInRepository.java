package com.ekhonni.backend.repository;

import com.ekhonni.backend.enums.TransactionStatus;
import com.ekhonni.backend.model.CashIn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: Asif Iqbal
 * Date: 2/10/25
 */
public interface CashInRepository extends BaseRepository<CashIn, Long> {

    Optional<CashIn> findByAccountId(Long accountId);

    void deleteByAccountId(Long accountId);

    boolean existsByAccountIdAndDeletedAtIsNull(Long accountId);

    @Modifying
    @Query("UPDATE CashIn SET sessionKey = :sessionKey, updatedAt = CURRENT_TIMESTAMP WHERE id = :id")
    void updateSessionKeyById(Long id, String sessionKey);

    <P> Page<P> findByAccountUserIdAndDeletedAtIsNull(UUID userId, Class<P> projection, Pageable pageable);

    <P> Page<P> findByStatus(TransactionStatus status, Class<P> projection, Pageable pageable);

    <P> Page<P> findByAccountUserIdAndStatus(UUID userId, TransactionStatus status, Class<P> projection, Pageable pageable);

    <P> Page<P> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Class<P> projection, Pageable pageable);

    Page<CashIn> findByStatusEqualsAndCreatedAtLessThanEqual(TransactionStatus status, LocalDateTime timestamp, Pageable pageable);

}