package com.ekhonni.backend.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 * Date: 2/4/25
 */
public interface BlockedUserProjection {

    @JsonProperty("id")
    UUID getUserId();

    @JsonProperty("name")
    String getUserName();

    @JsonProperty("email")
    String getUserEmail();

    @JsonProperty("address")
    String getUserAddress();

    @JsonProperty("profileImage")
    String getUserProfileImage();

    @JsonProperty("roleName")
    String getUserRoleName();

    @JsonProperty("verified")
    boolean getUserVerified();

    @JsonProperty("isBlocked")
    boolean getUserIsBlocked();

    @JsonProperty("blockedBy")
    String getBlockedByName();

    @JsonProperty("blockedReason")
    String getReason();

    @JsonProperty("blockedAt")
    LocalDateTime getBlockedAt();

    @JsonProperty("unblockAt")
    LocalDateTime getUnblockAt();

    @JsonProperty("createdAt")
    LocalDateTime getUserCreatedAt();

    @JsonProperty("updatedAt")
    LocalDateTime getUserUpdatedAt();

    @JsonProperty("deletedAt")
    LocalDateTime getUserDeletedAt();
}
