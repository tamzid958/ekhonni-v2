package com.ekhonni.backend.projection;

import java.time.LocalDateTime;

/**
 * Author: Safayet Rafi
 * Date: 12/31/24
 */

public interface NotificationDetailsProjection {

    String getMessage();

    LocalDateTime getCreatedAt();
}
