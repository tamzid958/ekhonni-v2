package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.UserDTO;
import com.ekhonni.backend.projection.UserProjection;
import com.ekhonni.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


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
    public UserProjection getUserById(@PathVariable UUID id) {
        return userService.getById(id);
    }


    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{id}/update")
    public UserDTO updateUserInfo(@PathVariable UUID id, @RequestBody UserDTO userDTO) {
        return userService.updateUserInfo(id, userDTO);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
