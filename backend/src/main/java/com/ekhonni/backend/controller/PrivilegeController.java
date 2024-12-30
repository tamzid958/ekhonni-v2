package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.PrivilegeDTO;
import com.ekhonni.backend.model.Privilege;
import com.ekhonni.backend.service.PrivilegeService;
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
@RequestMapping("/api/v2")
@AllArgsConstructor
@Validated
public class PrivilegeController {

    PrivilegeService privilegeService;

    @GetMapping("/privilege")
    public Page<Privilege> getAllPrivilege(Pageable pageable) {
        return privilegeService.getAll(pageable);
    }

    @GetMapping("role/{roleId}/privilege")
    public Page<Privilege> getAllPrivilegeByRole(@PathVariable("roleId") long roleId, Pageable pageable) {
        return privilegeService.getAllByRole(roleId, pageable);
    }

    @GetMapping("/privilege/{privilegeId}")
    public Privilege getPrivilegeById(@PathVariable("privilegeId") long privilegeId) {
        return privilegeService.get(privilegeId);
    }

    @PostMapping("/role/{roleId}/privilege/add")
    public String addPrivilege(@PathVariable("roleId") long roleId, @RequestBody PrivilegeDTO privilegeDTO) {
        return privilegeService.add(roleId, privilegeDTO);
    }

    @PatchMapping("/role/{roleId}/privilege/{privilegeId}/update")
    public PrivilegeDTO updatePrivilege(@PathVariable("privilegeId") long privilegeId, @RequestBody PrivilegeDTO privilegeDTO) {
        return privilegeService.update(privilegeId, privilegeDTO);
    }

    @DeleteMapping("/role/{roleId}/privilege/{privilegeId}/delete")
    public void deletePrivilege(@PathVariable("privilegeId") long privilegeId) {
        privilegeService.delete(privilegeId);
    }
}
