package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.EmailDTO;
import com.ekhonni.backend.projection.UserProjection;
import com.ekhonni.backend.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 * Date: 12/18/24
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/v2/admin")
public class AdminController {

    AdminService adminService;

    @PostMapping("/add-admin")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> add(@RequestBody EmailDTO emailDTO) {
        adminService.add(emailDTO.email());
        return ResponseEntity.ok("admin added");
    }

    @PostMapping("/remove-admin")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> remove(@RequestBody EmailDTO emailDTO) {
        adminService.remove(emailDTO.email());
        return ResponseEntity.ok("admin removed");
    }

    @GetMapping("/users")
    public Page<UserProjection> getAllUser(Pageable pageable) {
        return adminService.getAll(UserProjection.class, pageable);
    }

    @GetMapping("/users/deleted")
    public Page<UserProjection> getAllDeletedUser(Pageable pageable) {
        return adminService.getAllDeleted(UserProjection.class, pageable);
    }

    @PostMapping("/user/{id}/block")
    public void block(@PathVariable UUID id) {
        adminService.block(id);
    }

    @GetMapping("/users/blocked")
    public Page<UserProjection> getAllBlockedUser(Pageable pageable) {
        return adminService.getAllBlocked(UserProjection.class, pageable);
    }

    @GetMapping("/products")
    public void getAllProduct(Pageable pageable) {
        //To be implemented
    }

    @GetMapping("/products/deleted")
    public void getAllDeletedProduct(Pageable pageable) {
        // To be implemented
    }

    @PostMapping("/product/{id}/approve")
    public void approveProduct(Pageable pageable) {
        // To be implemented
    }

    @PostMapping("/product/{id}/decline")
    public void declineProduct(Pageable pageable) {
        // To be implemented
    }


    @PostMapping("/user/{id}/warn")
    public void warnUser() {
        // To be implemented
    }

}
