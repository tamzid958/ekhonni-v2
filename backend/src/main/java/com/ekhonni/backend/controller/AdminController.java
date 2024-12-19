package com.ekhonni.backend.controller;

import com.ekhonni.backend.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        return ResponseEntity.ok("admin added");
    }


}
