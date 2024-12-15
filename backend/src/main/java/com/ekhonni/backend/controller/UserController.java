package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.UserDTO;
import com.ekhonni.backend.projection.UserProjection;
import com.ekhonni.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public List<UserProjection> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasAuthority('ADMIN')")
    public UserProjection getById(@PathVariable UUID id) {
        return userService.getById(id);
    }


    @PatchMapping("/{id}/update")
    @PreAuthorize("#id == authentication.principal.id or hasAuthority('ADMIN')")
    public UserDTO update(@PathVariable UUID id, @RequestBody UserDTO userDTO) {
        return userService.update(id, userDTO);
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("#id == authentication.principal.id or hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
