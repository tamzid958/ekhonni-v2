/**
 * Author: Rifat Shariar Sakil
 * Time: 11:18 PM
 * Date: 1/9/2025
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto;

import com.ekhonni.backend.enums.ProductCondition;
import com.ekhonni.backend.validation.annotation.NonEmptyMultipartFile;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ProductUpdateDTO(
        @NotBlank(message = "Product name cannot be null")
        String name,
        @NotNull(message = "Price cannot be null")
        @Digits(integer = 10, fraction = 2, message = "Amount must have up to 10 integer digits and 2 decimal places")
        @Positive
        Double price,
        @NotBlank(message = "Description cannot be null")
        String description,
        @NotBlank(message = "Location cannot be null")
        String location,
        @NotNull(message = "Condition cannot be null")
        @Enumerated(EnumType.STRING)
        ProductCondition condition,
        @NotBlank(message = "Category cannot be null")
        String category,
        @NotNull(message = "Images list cannot be null")
        @Size(min = 2, max = 2, message = "Exactly 2 images are required")
        List<@NonEmptyMultipartFile MultipartFile> images
) {
}
