package com.ekhonni.backend.controller;

import com.ekhonni.backend.model.Role;
import com.ekhonni.backend.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Md Jahid Hasan
 * Date: 12/29/24
 */
@RestController
@RequestMapping("/api/v2/role")
@AllArgsConstructor
@Validated
public class RoleController {

    RoleService roleService;

    @GetMapping("/")
    public Page<Role> getAllRole(Pageable pageable) {
        return roleService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable long id) {
        return roleService.get(id);
    }

    @PostMapping("/add")
    public String addRole(@RequestBody Role role) {
        return roleService.add(role);
    }

    @PatchMapping("/{id}/update")
    public Role updateRole(@PathVariable long id, @RequestBody Role role) {
        return roleService.update(id, role);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteRole(@PathVariable long id) {
        roleService.delete(id);
    }
}
