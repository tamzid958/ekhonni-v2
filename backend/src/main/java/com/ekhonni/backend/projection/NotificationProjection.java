package com.ekhonni.backend.projection;

import com.ekhonni.backend.enums.NotificationStatus;

import java.util.UUID;

public interface NotificationProjection {

    Long getId();

    String getTitle();

    String getMessage();

    NotificationStatus getStatus();

    UUID getRecipientId();
}
