package com.ekhonni.backend.service;

import com.ekhonni.backend.projection.NotificationDetailsProjection;
import com.ekhonni.backend.projection.NotificationPreviewProjection;
import com.ekhonni.backend.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: Safayet Rafi
 * Date: 12/31/24
 */

@Setter
@Getter
@AllArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;


    public List<NotificationPreviewProjection> get(UUID userId) {
        return notificationRepository.findByRecipientId(userId).stream()
                .peek(preview -> {
                    System.out.println("Notification Title: " + preview.getTitle());
                    System.out.println("Time Ago: " + preview.getTimeAgo());
                })
                .toList();
    }

    public Optional<List<NotificationDetailsProjection>> get(UUID userId, Long id) {
        return notificationRepository.findByIdAndRecipientId(id, userId);
    }
}
