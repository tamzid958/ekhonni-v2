package com.ekhonni.backend.projection;

import com.ekhonni.backend.enums.NotificationType;

import java.time.LocalDateTime;

/**
 * Author: Safayet Rafi
 * Date: 01/02/25
 */

public interface NotificationPreviewProjection {

    Long getId();

    NotificationType getType();

    String getMessage();

    String getRedirectUrl();

    LocalDateTime getCreatedAt();

}
