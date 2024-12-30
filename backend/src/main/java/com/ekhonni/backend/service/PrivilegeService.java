package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.PrivilegeDTO;
import com.ekhonni.backend.exception.RoleNotFoundException;
import com.ekhonni.backend.model.Privilege;
import com.ekhonni.backend.model.Role;
import com.ekhonni.backend.repository.PrivilegeRepository;
import com.ekhonni.backend.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Author: Md Jahid Hasan
 * Date: 12/29/24
 */
@Service
public class PrivilegeService extends BaseService<Privilege, Long> {

    PrivilegeRepository privilegeRepository;
    RoleRepository roleRepository;

    public PrivilegeService(PrivilegeRepository privilegeRepository, RoleRepository roleRepository) {
        this.privilegeRepository = privilegeRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public String add(long roleId, PrivilegeDTO privilegeDTO) {

        Role role = roleRepository.findById(roleId).orElseThrow(RoleNotFoundException::new);

        Privilege privilege = new Privilege(
                privilegeDTO.name(),
                privilegeDTO.description(),
                role
        );

        privilegeRepository.save(privilege);
        return "privilege added";
    }

    public Page<Privilege> getAllByRole(long roleId, Pageable pageable) {
        return privilegeRepository.getAllByRole(roleId, pageable);
    }
}
