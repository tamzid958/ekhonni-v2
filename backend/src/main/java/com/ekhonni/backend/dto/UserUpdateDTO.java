package com.ekhonni.backend.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Author: Md Jahid Hasan
 * Date: 12/22/24
 */
public record UserUpdateDTO(@NotBlank(message = "name cannot be blank")
                            String name,
                            @NotBlank(message = "phone cannot be blank")
                            String phone,
                            @NotBlank(message = "address cannot be blank")
                            String address) {
}
