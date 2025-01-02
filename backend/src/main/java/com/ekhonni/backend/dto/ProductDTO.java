/**
 * Author: Rifat Shariar Sakil
 * Time: 12:50 AM
 * Date: 12/9/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto;

import com.ekhonni.backend.enums.ProductCondition;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

public record ProductDTO(@NotBlank
                         String name,
                         @Positive
                         @Column(nullable = false)
                         Double price,
                         @NotBlank
                         String description,
                         @Enumerated(EnumType.STRING)
                         @Column(nullable = false)
                         ProductCondition condition,
                         String category,
                         MultipartFile image
) {
    
}


