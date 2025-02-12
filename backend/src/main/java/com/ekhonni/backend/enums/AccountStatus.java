package com.ekhonni.backend.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Author: Asif Iqbal
 * Date: 1/30/25
 */
@Schema(description = "Represents the possible statuses of a user account.")
public enum AccountStatus {

    @Schema(description = "The account is active and fully operational.")
    ACTIVE,

    @Schema(description = "The account is inactive and may require verification or reactivation.")
    INACTIVE,

    @Schema(description = "The account has been temporarily suspended due to policy violations or security concerns.")
    SUSPENDED,

    @Schema(description = "The account has been permanently closed and cannot be used again.")
    CLOSED
}
