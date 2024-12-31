package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Notification;
import com.ekhonni.backend.projection.NotificationProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Author: Safayet Rafi
 * Date: 12/31/24
 */

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<NotificationProjection> findByRecipientId(UUID recipientId);
}
