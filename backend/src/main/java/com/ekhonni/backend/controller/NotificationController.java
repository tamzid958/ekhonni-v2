package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.NotificationPreviewDTO;
import com.ekhonni.backend.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<NotificationPreviewDTO> getPreviewNotifications(@PathVariable UUID id) {
        return notificationService.get(id);
    }
}
