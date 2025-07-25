package com.ekhonni.backend.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Author: Asif Iqbal
 * Date: 1/27/25
 */

@Schema(description = "DTO for creating a review")
public record ReviewCreateDTO(
        @Schema(description = "Unique identifier of the bid associated with the review", example = "123")
        @NotNull(message = "Bid id cannot be null")
        Long bidId,

        @Schema(description = "Rating given in the review in form of integer from 1 to 5", example = "5")
        @NotNull(message = "Rating cannot be null")
        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 5, message = "Rating must be at most 5")
        int rating,

        @Schema(description = "Optional description of the review", example = "Great product!")
        String description
) {
}
