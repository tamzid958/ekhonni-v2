package com.ekhonni.backend.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Author: Md Jahid Hasan
 */

public record UserDTO(@NotBlank(message = "name cannot be blank")
                      String name,
                      @NotBlank(message = "email cannot be blank")
                      @Email(message = "Not a valid email")
                      String email,
                      @NotBlank(message = "password cannot be blank")
                      String password,
                      @NotBlank(message = "phone cannot be blank")
                      String phone,
                      @NotBlank(message = "address cannot be blank")
                      String address) {
}
