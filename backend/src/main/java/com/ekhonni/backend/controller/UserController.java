package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.EmailDTO;
import com.ekhonni.backend.dto.PasswordDTO;
import com.ekhonni.backend.dto.RefreshTokenDTO;
import com.ekhonni.backend.dto.UserUpdateDTO;
import com.ekhonni.backend.model.AuthToken;
import com.ekhonni.backend.projection.UserProjection;
import com.ekhonni.backend.service.UserService;
import jakarta.validation.Valid;
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

    @GetMapping("/{id}")
    @PreAuthorize("(@userService.isActive(#id) && (#id == authentication.principal.id || !@userService.isSuperAdmin(#id)))")
    public UserProjection getUserById(@PathVariable UUID id) {
        return userService.get(id, UserProjection.class);
    }


    @PatchMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id && @userService.isActive(#id)")
    public UserUpdateDTO updateUser(@PathVariable UUID id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        return userService.update(id, userUpdateDTO);
    }

    @PatchMapping("/{id}/change-email")
    @PreAuthorize("#id == authentication.principal.id && @userService.isActive(#id)")
    public String updateUserEmail(@PathVariable UUID id, @Valid @RequestBody EmailDTO emailDTO) {
        return userService.updateEmail(id, emailDTO);
    }

    @PatchMapping("/{id}/change-password")
    @PreAuthorize("#id == authentication.principal.id && @userService.isActive(#id)")
    public String updateUserPassword(@PathVariable UUID id, @Valid @RequestBody PasswordDTO passwordDTO) {
        return userService.updatePassword(id, passwordDTO);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id && @userService.isActive(#id)")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/refresh-token/")
    @PreAuthorize("#id == authentication.principal.id && @userService.isActive(#id)")
    public AuthToken getNewAccessToken(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        return userService.getNewAccessToken(refreshTokenDTO);
    }

}
