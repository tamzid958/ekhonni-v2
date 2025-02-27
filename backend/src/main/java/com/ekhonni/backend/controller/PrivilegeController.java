package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.RoleIdsDTO;
import com.ekhonni.backend.model.PrivilegeDetailed;
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
@RequestMapping("/api/v2/privilege")
@AllArgsConstructor
@Validated
public class PrivilegeController {

    PrivilegeService privilegeService;


    @GetMapping("")
    public Page<PrivilegeDetailed> getAllPrivilege(Pageable pageable) {
        return privilegeService.getAll(pageable);
    }


    @GetMapping("/{privilegeId}")
    public PrivilegeDetailed getPrivilegeById(@PathVariable("privilegeId") long privilegeId) {
        return privilegeService.getById(privilegeId);
    }


    @PostMapping("/{privilegeId}/assign/role")
    public String assignMultipleRole(@PathVariable("privilegeId") long privilegeId, @Validated @RequestBody RoleIdsDTO RoleIdsDTO) {
        return privilegeService.assignMultipleRole(privilegeId, RoleIdsDTO);
    }

}
