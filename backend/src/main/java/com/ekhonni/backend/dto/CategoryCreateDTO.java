/**
 * Author: Rifat Shariar Sakil
 * Time: 6:16 PM
 * Date: 1/12/2025
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto;

import com.ekhonni.backend.validation.annotation.ImageOnly;
import com.ekhonni.backend.validation.annotation.NonEmptyMultipartFile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "DTO for creating a category with necessary information and image")
public record CategoryCreateDTO(
        @NotBlank(message = "Category name can not be null")
        String name,


//        @NonEmptyMultipartFile
//        @ImageOnly
//        @Size(min = 1, max = 1, message = "Exactly 1 images are required")
//        @Schema(description = "image of the category", example = "[\"image.jpg\"")
//        MultipartFile image,


        String parentCategory
) {
}
