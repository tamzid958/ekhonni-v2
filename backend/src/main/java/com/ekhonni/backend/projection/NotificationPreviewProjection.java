package com.ekhonni.backend.projection;

import java.time.LocalDateTime;

/**
 * Author: Safayet Rafi
 * Date: 01/02/25
 */

public interface NotificationPreviewProjection {

    String getMessage();

    LocalDateTime getCreatedAt();
}
