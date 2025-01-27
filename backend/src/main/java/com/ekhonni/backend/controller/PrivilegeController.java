package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.PrivilegeDTO;
import com.ekhonni.backend.exception.PrivilegeNotFoundException;
import com.ekhonni.backend.model.Privilege;
import com.ekhonni.backend.service.PrivilegeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public Page<Privilege> getAllPrivilege(Pageable pageable) {
        return privilegeService.getAll(pageable);
    }


    @GetMapping("/{privilegeId}")
    public Privilege getPrivilegeById(@PathVariable("privilegeId") long privilegeId) {
        return privilegeService.get(privilegeId).orElseThrow(() -> new PrivilegeNotFoundException("Privilege Not Found"));
    }

//    @PostMapping("/add")
//    public String addPrivilege(@RequestBody PrivilegeDTO privilegeDTO) {
//        return privilegeService.add(privilegeDTO);
//    }
//
//
//    @PostMapping("/add-multiple")
//    public String addMultiplePrivilege(@RequestBody List<PrivilegeDTO> privilegeDTOList) {
//        return privilegeService.addMultiple(privilegeDTOList);
//    }

    @PatchMapping("/{privilegeId}/update")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public PrivilegeDTO updatePrivilege(@PathVariable("privilegeId") long privilegeId, @RequestBody PrivilegeDTO privilegeDTO) {
        return privilegeService.update(privilegeId, privilegeDTO);
    }

    @DeleteMapping("/{privilegeId}/delete")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public void deletePrivilege(@PathVariable("privilegeId") long privilegeId) {
        privilegeService.deletePermanently(privilegeId);
    }
}
