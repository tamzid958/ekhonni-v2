package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.NotificationDetailsDTO;
import com.ekhonni.backend.dto.NotificationPreviewDTO;
import com.ekhonni.backend.repository.NotificationRepository;
import com.ekhonni.backend.util.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
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
    private final TimeUtils timeUtils;

    public List<NotificationPreviewDTO> get(UUID userId) {
        return notificationRepository.findByRecipientId(userId)
                .stream()
                .map(notifications -> new NotificationPreviewDTO(
                                notifications.getTitle(),
                                timeUtils.timeAgo(notifications.getCreatedAt())
                        )
                )
                .toList();
    }


    public NotificationDetailsDTO get(Long id) {
        return notificationRepository.findById(id)
                .map(notification -> new NotificationDetailsDTO(
                        notification.getMessage(),
                        timeUtils.timeAgo(notification.getCreatedAt())
                ))
                .orElseThrow(() -> new RuntimeException("Notification not found"));
    }
}
