package com.ekhonni.backend.dto.review;

import com.ekhonni.backend.enums.ReviewType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Author: Asif Iqbal
 * Date: 1/27/25
 */

@Schema(description = "DTO for creating a review")
public record ReviewUpdateDTO(
        @Schema(description = "Unique identifier of the bid associated with the review", example = "123")
        @NotNull(message = "Bid id cannot be null")
        Long bidId,

        @Schema(description = "Rating given in the review", example = "5")
        @NotNull(message = "Rating cannot be null")
        int rating,

        @Schema(description = "Optional description of the review", example = "Great product!")
        String description
) {
}
