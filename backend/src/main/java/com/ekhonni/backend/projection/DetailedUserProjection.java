package com.ekhonni.backend.projection;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 * Date: 2/2/25
 */
public interface DetailedUserProjection {

    UUID getId();

    String getName();

    String getEmail();

    String getAddress();

    String getProfileImage();

    String getRoleName();

    boolean getVerified();

    LocalDateTime getBlockedAt();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

    LocalDateTime getDeletedAt();

}
