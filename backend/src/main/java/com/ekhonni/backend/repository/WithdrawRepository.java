package com.ekhonni.backend.repository;

import com.ekhonni.backend.enums.WithdrawStatus;
import com.ekhonni.backend.model.Withdraw;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Author: Asif Iqbal
 * Date: 2/3/25
 */
@Repository
public interface WithdrawRepository extends BaseRepository<Withdraw, Long> {

    <P> Page<P> findByAccountUserIdAndDeletedAtIsNull(UUID userId, Class<P> projection, Pageable pageable);

    <P> Page<P> findByStatusAndDeletedAtIsNull(WithdrawStatus status, Class<P> projection, Pageable pageable);

    <P> Page<P> findByAccountUserIdAndStatusAndDeletedAtIsNull(UUID userId, WithdrawStatus status, Class<P> projection, Pageable pageable);

    <P> Page<P> findByCreatedAtBetweenAndDeletedAtIsNull(LocalDateTime startDate, LocalDateTime endDate, Class<P> projection, Pageable pageable);

    @Query("SELECT SUM(w.amount) FROM Withdraw w WHERE w.account.user.id = :userId AND w.status = :status AND w.deletedAt IS NULL")
    Double sumAmountByUserIdAndStatus(@Param("userId") Long userId, @Param("status") WithdrawStatus status);

}
