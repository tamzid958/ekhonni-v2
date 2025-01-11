package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.NotificationPreviewDTO;
import com.ekhonni.backend.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Author: Safayet Rafi
 * Date: 12/31/24
 */

@RestController
@RequestMapping("/api/v2/user")
@AllArgsConstructor
@Validated
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{id}/notifications")
    @PreAuthorize("#id == authentication.principal.id")
    public List<NotificationPreviewDTO> getPreviewNotifications(
            @PathVariable UUID id,
            @RequestParam(required = false) LocalDateTime lastFetchTime,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        return notificationService.getAllNew(id, lastFetchTime, pageable);
    }

    @DeleteMapping("/{userId}/notifications/{notificationId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public void delete(@PathVariable UUID userId, @PathVariable Long notificationId) {
        notificationService.delete(userId, notificationId);
    }
}
