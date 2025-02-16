package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.account.AccountReportProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends BaseRepository<Account, Long> {

    <P> Optional<P> findUserById(Long id, Class<P> projection);

    Optional<Account> findByUserId(UUID userId);

    <P> Optional<P> findByUserId(UUID userId, Class<P> projection);

    @Query("SELECT a FROM Account a WHERE a.user.role.name = 'SUPER_ADMIN'")
    Optional<Account> findSuperAdminAccount();

    @Query("""
       SELECT
         SUM(a.totalEarnings) AS totalEarnings,
         SUM(a.totalWithdrawals) AS totalWithdrawals,
         SUM(a.totalEarnings - a.totalWithdrawals) AS totalBalance
         FROM Account a
    """)
    AccountReportProjection getAccountReport();

    @Query("""
    SELECT
        SUM(a.totalEarnings) AS totalEarnings,
        SUM(a.totalWithdrawals) AS totalWithdrawals,
        SUM(a.totalEarnings - a.totalWithdrawals) AS totalBalance
        FROM Account a
    WHERE a.createdAt BETWEEN :startTime AND :endTime
    """)
    AccountReportProjection getAccountReportBetween(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

}
