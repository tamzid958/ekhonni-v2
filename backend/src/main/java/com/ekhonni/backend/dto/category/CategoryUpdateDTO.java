/**
 * Author: Rifat Shariar Sakil
 * Time: 12:02 PM
 * Date: 12/30/2024
 * Project Name: backend
 */

package com.ekhonni.backend.dto.category;

import com.ekhonni.backend.validation.annotation.ImageOnly;
import com.ekhonni.backend.validation.annotation.NonEmptyMultipartFile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record CategoryUpdateDTO(
        @Column(nullable = false, unique = true)
        String name,
        Boolean active,
        @NonEmptyMultipartFile
        @ImageOnly
        @Size(min = 1, max = 1, message = "Exactly 1 images are required")
        @Schema(description = "image of the category", example = "[\"image.jpg\"")
        MultipartFile image
) {
}
