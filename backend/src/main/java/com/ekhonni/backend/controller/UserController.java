package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.UserDTO;
import com.ekhonni.backend.projection.UserProjection;
import com.ekhonni.backend.service.UserService;
import org.springframework.http.HttpStatus;
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

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping
    public List<UserProjection> getAll() {
        return userService.getAll();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("/{id}")
    public UserProjection getById(@PathVariable UUID id) {
        return userService.getById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public UserDTO create(@RequestBody UserDTO userDTO) {
        return userService.create(userDTO);
    }


    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("/{id}/update")
    public UserDTO update(@PathVariable UUID id, @RequestBody UserDTO userDTO) {
        return userService.update(id, userDTO);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
