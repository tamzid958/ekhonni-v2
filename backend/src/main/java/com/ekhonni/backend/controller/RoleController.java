package com.ekhonni.backend.controller;

import com.ekhonni.backend.exception.role.RoleNotFoundException;
import com.ekhonni.backend.model.Privilege;
import com.ekhonni.backend.model.Role;
import com.ekhonni.backend.projection.UserProjection;
import com.ekhonni.backend.service.PrivilegeService;
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
    PrivilegeService privilegeService;

    @GetMapping("/")
    public Page<Role> getAllRole(Pageable pageable) {
        return roleService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable long id) {
        return roleService.get(id).orElseThrow(() -> new RoleNotFoundException("Role not found"));
    }

    @PostMapping("/")
    public String addRole(@RequestBody Role role) {
        return roleService.add(role);
    }

    @PatchMapping("/{id}")
    public Role updateRole(@PathVariable long id, @RequestBody Role role) {
        return roleService.update(id, role);
    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable long id) {
        roleService.delete(id);
    }

    @PostMapping("/{roleId}/assign/privilege/{privilegeId}")
    public String assignPrivilege(@PathVariable("roleId") long roleId, @PathVariable("privilegeId") long privilegeId) {
        return privilegeService.assign(roleId, privilegeId);
    }

    @PostMapping("/{roleId}/remove/privilege/{privilegeId}")
    public String removePrivilege(@PathVariable("roleId") long roleId, @PathVariable("privilegeId") long privilegeId) {
        return privilegeService.remove(roleId, privilegeId);
    }

    @GetMapping("/{roleId}/privilege/")
    public Page<Privilege> getAllPrivilegeOfRole(@PathVariable("roleId") long roleId, Pageable pageable) {
        return privilegeService.getAllOfRole(roleId, pageable);
    }

    @GetMapping("/{roleId}/users/")
    public Page<UserProjection> getAllUsersAssignedToRole(@PathVariable("roleId") long roleId, Pageable pageable) {
        return roleService.getAllUserAssigned(roleId, UserProjection.class, pageable);
    }
}
