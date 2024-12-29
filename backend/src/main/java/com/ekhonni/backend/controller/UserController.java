package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.EmailDTO;
import com.ekhonni.backend.dto.PasswordDTO;
import com.ekhonni.backend.dto.UserUpdateDTO;
import com.ekhonni.backend.projection.UserProjection;
import com.ekhonni.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 */

@RestController
@RequestMapping("/api/v2/user")
@AllArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}/profile")
    public UserProjection getUserById(@PathVariable UUID id) {
        return userService.get(id, UserProjection.class);
    }


    @PatchMapping("/{id}/update")
    @PreAuthorize("#id == authentication.principal.id")
    public UserUpdateDTO updateUser(@PathVariable UUID id, @RequestBody UserUpdateDTO userUpdateDTO) {
        return userService.update(id, userUpdateDTO);
    }

    @PatchMapping("/{id}/change-email")
    @PreAuthorize("#id == authentication.principal.id")
    public String updateUserEmail(@PathVariable UUID id, @RequestBody EmailDTO emailDTO) {
        return userService.updateEmail(id, emailDTO);
    }

    @PatchMapping("/{id}/change-password")
    @PreAuthorize("#id == authentication.principal.id")
    public String updateUserPassword(@PathVariable UUID id, @RequestBody PasswordDTO passwordDTO) {
        return userService.updatePassword(id, passwordDTO);
    }


    @DeleteMapping("/{id}/delete")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/wishlists")
    public void getUserWishlists() {
        // To be implemented
    }

    @GetMapping("/{id}/bids")
    public void getUserBids() {
        // To be implemented
    }

    @GetMapping("/{id}/products")
    public void getUploadedProducts() {
        // To be implemented
    }
}
