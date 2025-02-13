package com.ekhonni.backend.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Author: Md Jahid Hasan
 * Date: 2/13/25
 */
public record PrivilegeIdsDTO(
        @NotNull
        List<Long> privilegeIds
) {
}
