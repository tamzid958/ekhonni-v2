package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.UserDTO;
import com.ekhonni.backend.projection.UserProjection;
import com.ekhonni.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 */

@RestController
@RequestMapping("/api/v2/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<UserProjection> getAll(Pageable pageable) {
        return userService.getAll(UserProjection.class, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasAuthority('ADMIN')")
    public UserProjection getById(@PathVariable UUID id) {
        return userService.get(id, UserProjection.class);
    }


    @PatchMapping("/{id}/update")
    @PreAuthorize("#id == authentication.principal.id or hasAuthority('ADMIN')")
    public UserDTO update(@PathVariable UUID id, @RequestBody UserDTO userDTO) {
        return userService.update(id, userDTO);
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("#id == authentication.principal.id or hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.softDelete(id);
        return ResponseEntity.noContent().build();
    }


}
