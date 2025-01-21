package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.NotificationCreateRequestDTO;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Operation(
            description = "Fetches notifications for a specific user with optional pagination and filtering by last fetch time.",
            parameters = {
                    @Parameter(name = "userId", description = "The unique ID of the user", required = true),
                    @Parameter(name = "lastFetchTime", description = "Optional timestamp to fetch new notifications after last time"),
                    @Parameter(name = "pageable", description = "Pagination parameters for notifications")
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Notifications fetched successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "204",
                            description = "No new notifications available",
                            content = @Content(mediaType = "application/json")
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "Unauthorized access",
                            content = @Content(mediaType = "application/json")
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
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
