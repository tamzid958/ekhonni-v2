/**
 * Author: Rifat Shariar Sakil
 * Time: 6:16 PM
 * Date: 1/12/2025
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreateDTO(
        @NotBlank(message = "Category name can not be null")
        String name,
        String parentCategory
) {
}
