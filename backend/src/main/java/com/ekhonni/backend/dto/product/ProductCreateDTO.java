/**
 * Author: Rifat Shariar Sakil
 * Time: 12:50 AM
 * Date: 12/9/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto.product;

import com.ekhonni.backend.enums.Division;
import com.ekhonni.backend.enums.ProductCondition;
import com.ekhonni.backend.validation.annotation.ImageOnly;
import com.ekhonni.backend.validation.annotation.NonEmptyMultipartFile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Schema(description = "DTO for creating a product with necessary information and images")
public record ProductCreateDTO(
        @NotBlank(message = "Product title cannot be null")
        @Schema(description = "The title of the product", example = "Smartphone X")
        String title,

        @NotBlank(message = "Product subTitle cannot be null")
        @Schema(description = "A brief subtitle or tagline for the product", example = "Latest in tech")
        String subTitle,

        @NotBlank(message = "Description cannot be null")
        @Schema(description = "Detailed description of the product", example = "The Smartphone X is a high-end device with advanced features.")
        String description,

        @NotNull(message = "Price cannot be null")
        @Digits(integer = 10, fraction = 2, message = "Amount must have up to 10 integer digits and 2 decimal places")
        @Positive
        @Schema(description = "Price of the product", example = "299.99")
        Double price,

        @NotNull(message = "Product Location Division cannot be empty")
        @Enumerated(EnumType.STRING)
        @Schema(description = "The division where the product is located", example = "Dhaka")
        Division division,

        @NotBlank(message = "Location cannot be null")
        @Schema(description = "The physical location of the product", example = "12/1, Main St, Dhaka")
        String address,

        @NotNull(message = "Condition cannot be null")
        @Enumerated(EnumType.STRING)
        @Schema(description = "The condition of the product", example = "NEW")
        ProductCondition condition,

        @NotBlank
        @Schema(description = "Details about the condition of the product", example = "Brand new, unopened")
        String conditionDetails,

        @NotBlank(message = "Category cannot be null")
        @Schema(description = "Category of the product", example = "Electronics")
        String category,

        @NonEmptyMultipartFile
        @ImageOnly
        @Size(min = 2, max = 2, message = "Exactly 2 images are required")
        @Schema(description = "List of images of the product", example = "[\"image1.jpg\", \"image2.jpg\"]")
        List<MultipartFile> images
) {
}

