/**
 * Author: Rifat Shariar Sakil
 * Time: 2:58 PM
 * Date: 12/8/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryDTO(
        @NotBlank
        String name,
        String parentCategory
) {
}
