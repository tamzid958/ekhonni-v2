package com.ekhonni.backend.dto;


public record UserDTO(String name,
                      String email,
                      String password,
                      String phone,
                      String address) {
}
