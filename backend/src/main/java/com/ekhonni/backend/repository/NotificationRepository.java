package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Notification;
import com.ekhonni.backend.projection.NotificationPreviewProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Author: Safayet Rafi
 * Date: 12/31/24
 */

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<NotificationPreviewProjection> findByRecipientIdOrRecipientIdIsNull(UUID recipientId, Pageable pageable);

    @Query("""
    SELECT n FROM Notification n
    WHERE (n.recipient.id = :recipientId OR n.recipient IS NULL)
    AND (n.createdAt > :lastFetchTime)
""")
    List<NotificationPreviewProjection> findNewNotifications(
            @Param("recipientId") UUID recipientId,
            @Param("lastFetchTime") LocalDateTime lastFetchTime,
            Pageable pageable
    );

}
