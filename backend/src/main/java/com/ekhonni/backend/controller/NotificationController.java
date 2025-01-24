package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.NotificationCreateRequestDTO;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.NotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Author: Safayet Rafi
 * Date: 12/31/24
 */

@RestController
@RequestMapping("/api/v2/user/{userId}/notifications")
@AllArgsConstructor
@Validated
@Tag(name = "Notification", description = "APIs for managing user notifications")
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * ---------User API---------
     */

    @GetMapping
    @PreAuthorize("#userId == authentication.principal.id")
    public DeferredResult<ApiResponse<?>> get(
            @PathVariable UUID userId,
            @RequestParam(required = false) LocalDateTime lastFetchTime,
            Pageable pageable
    ) {
        return notificationService.handleLongPolling(userId, lastFetchTime, pageable);
    }


    @GetMapping("/{notificationId}/redirect")
    @PreAuthorize("#userId == authentication.principal.id")
    public ApiResponse<?> redirect(@PathVariable UUID userId, @PathVariable Long notificationId) {
        return notificationService.redirect(notificationId);
    }


    /**
     * ---------Admin API---------
     */

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ApiResponse<?> create(@RequestBody NotificationCreateRequestDTO notificationCreateRequestDTO) {
        return notificationService.createForAllUser(notificationCreateRequestDTO);
    }
}
