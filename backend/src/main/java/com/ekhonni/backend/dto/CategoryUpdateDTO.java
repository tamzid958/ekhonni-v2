/**
 * Author: Rifat Shariar Sakil
 * Time: 12:02 PM
 * Date: 12/30/2024
 * Project Name: backend
 */

package com.ekhonni.backend.dto;

import jakarta.persistence.Column;

public record CategoryUpdateDTO(
        @Column(nullable = false, unique = true)
        String name,
        boolean active
) {
}
