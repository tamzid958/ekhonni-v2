package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.role.RoleCreateDTO;
import com.ekhonni.backend.exception.role.RoleAlreadyExistsException;
import com.ekhonni.backend.exception.role.RoleCannotBeDeletedException;
import com.ekhonni.backend.exception.role.RoleNotFoundException;
import com.ekhonni.backend.exception.user.UserNotFoundException;
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
        Role role = roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException("Role not found while deleting"));

        if (Objects.equals(role.getName(), "SUPER_ADMIN") || Objects.equals(role.getName(), "USER")) {
            throw new RoleCannotBeDeletedException();
        }
        roleRepository.softDelete(id);
    }

    @Transactional
    public String add(RoleCreateDTO dto) {
        String roleName = dto.name().toUpperCase();
        if (roleRepository.existsByName(roleName)) throw new RoleAlreadyExistsException();
        Role newRole = new Role(
                roleName,
                dto.description()
        );

        roleRepository.save(newRole);

        return "New Role added: " + newRole.getName();
    }

    public boolean hasPrivilegeAccess(Role role, Privilege privilege) {
        if (role == null || privilege == null) return false;

        List<Privilege> privilegeList = privilegeService.getAllOfRole(role);

        return privilegeList.stream().anyMatch(p -> p.getId().equals(privilege.getId()));
    }

    @Transactional
    @Modifying
    public String assign(UUID userId, long roleId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found when assigning role"));

        Role currentRole = user.getRole();

        Role newRole = roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException("Role not found while assigning"));

        validateRoleAssignment(currentRole, newRole);

        user.setRole(newRole);

        return "Role assigned to user";
    }

    private void validateRoleAssignment(Role currentRole, Role newRole) {
        if (newRole.getName().equals("SUPER_ADMIN"))
            throw new IllegalArgumentException("Super Admin role cannot be set to user");
        if (newRole.getName().equals(currentRole.getName()))
            throw new IllegalArgumentException("Already a " + newRole.getName());
    }

    public Page<UserProjection> getAllUserAssigned(long roleId, Class<UserProjection> projection, Pageable pageable) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new UserNotFoundException("User not found when searching assigned user"));
        return userRepository.getAllByRole(role, UserProjection.class, pageable);
    }

    @Transactional
    @Modifying
    public String remove(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found when removing role"));

        Role currentRole = user.getRole();

        validateRoleRemoval(currentRole);

        user.setRole(roleRepository.findByName("USER").orElseThrow(() -> new RoleNotFoundException("Role not found while removing")));

        return "Role removed from user";
    }

    private void validateRoleRemoval(Role currentRole) {
        if (currentRole.getName().equals("SUPER_ADMIN"))
            throw new IllegalArgumentException("Super Admin role cannot be removed");
        if (currentRole.getName().equals("USER"))
            throw new IllegalArgumentException("Default role USER cannot be removed");

    }
}
