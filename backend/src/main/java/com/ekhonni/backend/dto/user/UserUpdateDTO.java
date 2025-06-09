package com.ekhonni.backend.dto.user;

import jakarta.validation.constraints.NotNull;

/**
 * Author: Md Jahid Hasan
 * Date: 12/22/24
 */
public record UserUpdateDTO(@NotNull(message = "name cannot be null")
                            String name,
                            @NotNull(message = "phone cannot be null")
                            String phone,
                            @NotNull(message = "address cannot be null")
                            String address) {
}
