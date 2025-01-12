package com.ekhonni.backend.controller;

import com.ekhonni.backend.projection.UserProjection;
import com.ekhonni.backend.service.AdminService;
import com.ekhonni.backend.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final AdminService adminService;
    private final RoleService roleService;

//    @PostMapping("/add-admin")
//    @PreAuthorize("hasAuthority('SUPER_ADMIN') && @userService.isActive(emailDTO.email())")
//    public ResponseEntity<?> add(@RequestBody EmailDTO emailDTO) {
//        adminService.add(emailDTO.email());
//        return ResponseEntity.ok("admin added");
//    }
//
//    @PostMapping("/remove-admin")
//    @PreAuthorize("hasAuthority('SUPER_ADMIN') && @userService.isActive(emailDTO.email())")
//    public ResponseEntity<?> remove(@RequestBody EmailDTO emailDTO) {
//        adminService.remove(emailDTO.email());
//        return ResponseEntity.ok("admin removed");
//    }

    @GetMapping("/users/")
    public Page<UserProjection> getAllUser(Pageable pageable) {
        return adminService.getAllUser(UserProjection.class, pageable);
    }

    @PostMapping("user/{userId}/assign/role/{roleId}")
    @PreAuthorize("@userService.isActive(#userId)")
    public String assignRole(@PathVariable("userId") UUID userId, @PathVariable("roleId") long roleId) {
        return roleService.assign(userId, roleId);
    }

    @PostMapping("user/{userId}/remove/role/")
    @PreAuthorize("@userService.isActive(#userId)")
    public String removeRole(@PathVariable("userId") UUID userId) {
        return roleService.remove(userId);
    }

    @GetMapping("/users/deleted/")
    public Page<UserProjection> getAllDeletedUser(Pageable pageable) {
        return adminService.getAllDeleted(UserProjection.class, pageable);
    }

    @PostMapping("/user/{id}/block")
    @PreAuthorize("@userService.isActive(#id)")
    public void block(@PathVariable UUID id) {
        adminService.block(id);
    }

    @GetMapping("/users/blocked/")
    public Page<UserProjection> getAllBlockedUser(Pageable pageable) {
        return adminService.getAllBlocked(UserProjection.class, pageable);
    }


    @PostMapping("/user/{id}/warn")
    public void warnUser() {
        // To be implemented
    }

}