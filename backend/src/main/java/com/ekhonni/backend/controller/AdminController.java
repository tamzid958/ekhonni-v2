package com.ekhonni.backend.controller;

import com.ekhonni.backend.projection.UserProjection;
import com.ekhonni.backend.service.AdminService;
import com.ekhonni.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Md Jahid Hasan
 * Date: 12/18/24
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/v2/admin")
public class AdminController {

    AdminService adminService;
    UserService userService;

    @PostMapping("/add-admin")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> add(String email) {
        adminService.add(email);
        return ResponseEntity.ok("admin added");
    }

    @PostMapping("/remove-admin")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> remove(String email) {
        adminService.remove(email);
        return ResponseEntity.ok("admin removed");
    }

    @GetMapping("/users")
    public Page<UserProjection> getAllUser(Pageable pageable) {
        return userService.getAll(UserProjection.class, pageable);
    }

    @GetMapping("/users/deleted")
    public Page<UserProjection> getAllDeletedUser(Pageable pageable) {
        return userService.getAllDeleted(UserProjection.class, pageable);
    }

    @GetMapping("/users/blocked")
    public void getAllBlockedUser(Pageable pageable) {
        // To be implemented
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


    @PostMapping("/user/{id}/block")
    public void blockUser() {
        // To be implemented
    }

    @PostMapping("/user/{id}/warn")
    public void warnUser() {
        // To be implemented
    }

}