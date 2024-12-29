/**
 * Author: Rifat Shariar Sakil
 * Time: 2:58 PM
 * Date: 12/8/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto;

import jakarta.persistence.Column;

public record CategoryDTO(
        @Column(nullable = false, unique = true)
        String name,
        String parentCategory
) {
}
