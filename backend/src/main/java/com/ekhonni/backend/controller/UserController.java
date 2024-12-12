package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.UserDTO;
import com.ekhonni.backend.projection.UserProjection;
import com.ekhonni.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 */

@RestController
@RequestMapping("/api/v2/user")
public record UserController(UserService userService) {


    @GetMapping
    public List<UserProjection> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserProjection getById(@PathVariable UUID id) {
        return userService.getById(id);
    }


    @PatchMapping("/{id}/update")
    public UserDTO update(@PathVariable UUID id, @RequestBody UserDTO userDTO) {
        return userService.update(id, userDTO);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
