package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Notification;
import com.ekhonni.backend.projection.NotificationPreviewProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Author: Safayet Rafi
 * Date: 12/31/24
 */

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<NotificationPreviewProjection> findByRecipientId(UUID recipientId, Pageable pageable);

    List<NotificationPreviewProjection> findByRecipientIdAndCreatedAtAfter(UUID recipientId, LocalDateTime createdAt, Pageable pageable);

}
