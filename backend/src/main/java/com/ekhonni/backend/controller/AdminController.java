package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.UserIdDTO;
import com.ekhonni.backend.projection.DetailedUserProjection;
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


    @GetMapping("/user")
    public Page<DetailedUserProjection> getAllUserByNameOrEmail(Pageable pageable, @RequestParam(required = false) String name, String email) {
        if (name != null || email != null) {
            return adminService.getAllUserByNameOrEmail(DetailedUserProjection.class, pageable, name, email);
        } else {
            return adminService.getAll(DetailedUserProjection.class, pageable);
        }
    }

    @GetMapping("/user/delete")
    public Page<UserProjection> getAllDeletedUser(Pageable pageable) {
        return adminService.getAllDeleted(UserProjection.class, pageable);
    }

    @GetMapping("/user/block")
    public Page<DetailedUserProjection> getAllBlockedUser(Pageable pageable) {
        return adminService.getAllBlocked(DetailedUserProjection.class, pageable);
    }

    @GetMapping("/user/active")
    public Page<DetailedUserProjection> getAllActiveUser(Pageable pageable) {
        return adminService.getAllActive(DetailedUserProjection.class, pageable);
    }

    @PostMapping("/user/block")
    public void block(@RequestBody UserIdDTO userIdDTO) {
        adminService.block(userIdDTO.id());
    }

    @PostMapping("/user/unblock")
    public void unblock(@RequestBody UserIdDTO userIdDTO) {
        adminService.unblock(userIdDTO.id());
    }


    @PostMapping("/user/{id}/warn")
    public void warnUser() {
        // To be implemented
    }

}