package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.VerificationToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * Author: Safayet Rafi
 * Date: 22/12/24
 */

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);

    VerificationToken findByUserId(UUID userId);



    @Query("""
                SELECT t.user.id
                FROM VerificationToken t
                WHERE t.expiryDate < :now AND t.user.verified = false
            """)
    List<UUID> findUnverifiedUserIdsWithExpiredTokens(LocalDateTime now);

    @Modifying
    @Transactional
    void deleteByExpiryDateBefore(LocalDateTime now);

}
