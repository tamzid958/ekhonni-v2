package com.ekhonni.backend.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Author: Md Jahid Hasan
 * Date: 2/24/25
 */
public record RoleIdsDTO(
        @NotNull
        List<Long> roleIds
) {
}
