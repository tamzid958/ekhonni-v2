package com.ekhonni.backend.service;

import com.ekhonni.backend.exception.RoleAlreadyExistsException;
import com.ekhonni.backend.exception.RoleCannotBeDeletedException;
import com.ekhonni.backend.exception.RoleNotFoundException;
import com.ekhonni.backend.exception.UserNotFoundException;
import com.ekhonni.backend.model.Privilege;
import com.ekhonni.backend.model.Role;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.UserProjection;
import com.ekhonni.backend.repository.RoleRepository;
import com.ekhonni.backend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 * Date: 12/29/24
 */
@Service
public class RoleService extends BaseService<Role, Long> {

    private final RoleRepository roleRepository;
    private final PrivilegeService privilegeService;
    private final UserRepository userRepository;

    public RoleService(RoleRepository roleRepository, PrivilegeService privilegeService, UserRepository userRepository) {
        super(roleRepository);
        this.roleRepository = roleRepository;
        this.privilegeService = privilegeService;
        this.userRepository = userRepository;
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

    public boolean hasPrivilegeAccess(Role role, Privilege privilege) {
        if (role == null || privilege == null) return false;

        List<Privilege> privilegeList = privilegeService.getAllOfRole(role);

        return privilegeList.stream().anyMatch(p -> p.getId().equals(privilege.getId()));
    }

    @Transactional
    @Modifying
    public String assign(UUID userId, long roleId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Role role = roleRepository.findById(roleId).orElseThrow(RoleNotFoundException::new);

        if (!role.getName().equals("SUPER_ADMIN")) user.setRole(role);
        else throw new RuntimeException("Super Admin role cannot be set to user");
        return "Role assigned to user";
    }

    public Page<UserProjection> getAllUserAssigned(long roleId, Class<UserProjection> projection, Pageable pageable) {
        return userRepository.getAllByRoleId(roleId, UserProjection.class, pageable);
    }
}
