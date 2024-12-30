package com.ekhonni.backend.service;

import com.ekhonni.backend.model.Role;
import com.ekhonni.backend.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author: Md Jahid Hasan
 * Date: 12/29/24
 */
@Service
public class RoleService extends BaseService<Role, Long> {

    RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public String add(Role role) {
        Role newRole = new Role(
                role.getName(),
                role.getDescription()
        );

        roleRepository.save(newRole);

        return "New Role added";
    }
}
