package com.ekhonni.backend.controller;

import com.ekhonni.backend.exception.PrivilegeNotFoundException;
import com.ekhonni.backend.model.Privilege;
import com.ekhonni.backend.service.PrivilegeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
        return privilegeService.findById(privilegeId).orElseThrow(() -> new PrivilegeNotFoundException("Privilege Not Found"));
    }

}
