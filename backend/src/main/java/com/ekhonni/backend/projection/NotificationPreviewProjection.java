package com.ekhonni.backend.projection;

import com.ekhonni.backend.util.TimeUtils;

import java.time.LocalDateTime;

public interface NotificationPreviewProjection {

    String getTitle();

    LocalDateTime getCreatedAt();

    default String getTimeAgo() {
        return TimeUtils.timeAgo(getCreatedAt());
    }

}
