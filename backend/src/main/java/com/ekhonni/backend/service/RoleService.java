package com.ekhonni.backend.service;

import com.ekhonni.backend.exception.RoleAlreadyExistsException;
import com.ekhonni.backend.exception.RoleCannotBeDeletedException;
import com.ekhonni.backend.exception.RoleNotFoundException;
import com.ekhonni.backend.model.Role;
import com.ekhonni.backend.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Author: Md Jahid Hasan
 * Date: 12/29/24
 */
@Service
public class RoleService extends BaseService<Role, Long> {

    RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        super(roleRepository);
        this.roleRepository = roleRepository;
    }

    public void delete(long id) {
        Role role = roleRepository.findById(id).orElseThrow(RoleNotFoundException::new);

        if (Objects.equals(role.getName(), "SUPER_ADMIN") || Objects.equals(role.getName(), "USER")) {
            throw new RoleCannotBeDeletedException();
        }
        roleRepository.deleteById(id);
    }

    @Transactional
    public String add(Role role) {
        if (roleRepository.existsByName("ADMIN")) throw new RoleAlreadyExistsException();
        Role newRole = new Role(
                role.getName(),
                role.getDescription()
        );

        roleRepository.save(newRole);

        return "New Role added";
    }
}
