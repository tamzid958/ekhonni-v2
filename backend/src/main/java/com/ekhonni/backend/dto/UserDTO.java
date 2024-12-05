package com.ekhonni.backend.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(@NotBlank
                      String name,
                      @NotBlank
                      @Email
                      String email,
                      @NotBlank
                      String password,
                      @NotBlank
                      String phone,
                      @NotBlank
                      String address) {
}
