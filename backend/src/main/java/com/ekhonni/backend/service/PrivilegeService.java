package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.PrivilegeDTO;
import com.ekhonni.backend.dto.PrivilegeIdsDTO;
import com.ekhonni.backend.exception.prvilege.NoResourceFoundException;
import com.ekhonni.backend.exception.prvilege.PrivilegeNotFoundException;
import com.ekhonni.backend.exception.role.RoleNotFoundException;
import com.ekhonni.backend.model.Privilege;
import com.ekhonni.backend.model.Role;
import com.ekhonni.backend.model.RolePrivilegeAssignment;
import com.ekhonni.backend.repository.RolePrivilegeAssignmentRepository;
import com.ekhonni.backend.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


/**
 * Author: Md Jahid Hasan
 * Date: 12/29/24
 */
@Service
public class PrivilegeService {

    private final Set<Privilege> privileges = new HashSet<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    RoleRepository roleRepository;
    RolePrivilegeAssignmentRepository rolePrivilegeAssignmentRepository;

    public PrivilegeService(RoleRepository roleRepository, RolePrivilegeAssignmentRepository rolePrivilegeAssignmentRepository) {
        this.roleRepository = roleRepository;
        this.rolePrivilegeAssignmentRepository = rolePrivilegeAssignmentRepository;
    }


    @Transactional
    public void addMultiple(List<PrivilegeDTO> privilegeDTOList) {

        for (PrivilegeDTO privilegeDTO : privilegeDTOList) {
            this.add(privilegeDTO);
        }

    }


    @Transactional
    public void add(PrivilegeDTO privilegeDTO) {

        if (!this.existsByHttpMethodAndEndpoint(privilegeDTO.httpMethod(), privilegeDTO.endpoint())) {
            Privilege privilege = new Privilege(
                    generateUniqueId(),
                    privilegeDTO.name(),
                    privilegeDTO.description(),
                    privilegeDTO.type(),
                    privilegeDTO.httpMethod(),
                    privilegeDTO.endpoint()
            );

            privileges.add(privilege);
            assignPrivilegeToSuperAdmin(privilege);
        }

    }

    private Long generateUniqueId() {
        return idGenerator.getAndIncrement();
    }


    private void assignPrivilegeToSuperAdmin(Privilege privilege) {
        Role roleAdmin = roleRepository.findByName("SUPER_ADMIN").orElseThrow(() -> new RoleNotFoundException("Role not found when assigning"));

        RolePrivilegeAssignment rolePrivilegeAssignment = new RolePrivilegeAssignment(roleAdmin, privilege.getId());

        rolePrivilegeAssignmentRepository.save(rolePrivilegeAssignment);
    }


    public String assign(long roleId, long privilegeId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException("Role not found when assigning"));
        Privilege privilege = this.findById(privilegeId).orElseThrow(() -> new PrivilegeNotFoundException("Privilege not found"));

        if (!rolePrivilegeAssignmentRepository.existsByRoleAndPrivilegeId(role, privilegeId)) {
            RolePrivilegeAssignment rolePrivilegeAssignment = new RolePrivilegeAssignment(role, privilege.getId());
            rolePrivilegeAssignmentRepository.save(rolePrivilegeAssignment);
        }

        return "Privilege assigned to role";
    }

    public String assignMultiple(long roleId, PrivilegeIdsDTO privilegeIdsDTO) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException("Role not found when assigning"));

        for (long privilegeId : privilegeIdsDTO.privilegeIds()) {
            Privilege privilege = this.findById(privilegeId).orElseThrow(() -> new PrivilegeNotFoundException("Privilege not found"));

            if (!rolePrivilegeAssignmentRepository.existsByRoleAndPrivilegeId(role, privilegeId)) {
                RolePrivilegeAssignment rolePrivilegeAssignment = new RolePrivilegeAssignment(role, privilege.getId());
                rolePrivilegeAssignmentRepository.save(rolePrivilegeAssignment);
            }
        }

        return "Privileges assigned to role";
    }

    public Page<Privilege> getAllOfRole(long roleId, Pageable pageable) {
        Page<RolePrivilegeAssignment> rolePrivilegeAssignments = rolePrivilegeAssignmentRepository.findAllByRoleId(roleId, pageable);

        List<Privilege> privilegesOfRole = rolePrivilegeAssignments.stream()
                .map(assignment -> this.findById(assignment.getPrivilegeId())
                        .orElseThrow(() -> new PrivilegeNotFoundException("Privilege not found ")))
                .collect(Collectors.toList());

        return new PageImpl<>(privilegesOfRole, pageable, rolePrivilegeAssignments.getTotalElements());
    }

    public List<Privilege> getAllOfRole(Role role) {
        long roleId = role.getId();

        List<RolePrivilegeAssignment> rolePrivilegeAssignments = rolePrivilegeAssignmentRepository.findAllByRoleId(roleId);

        return rolePrivilegeAssignments.stream()
                .map(assignment -> this.findById(assignment.getPrivilegeId())
                        .orElseThrow(() -> new PrivilegeNotFoundException("Privilege not found with id: " + assignment.getPrivilegeId())))
                .collect(Collectors.toList());
    }

    public Privilege getByHttpMethodAndEndpoint(String httpMethod, String endpoint) {

        return this.findByHttpMethodAndEndpoint(httpMethod, endpoint).orElseThrow(NoResourceFoundException::new);
    }

    public String remove(long roleId, long privilegeId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException("Role not found when removing"));
        this.findById(privilegeId).orElseThrow(() -> new PrivilegeNotFoundException("Privilege Not found while removing"));

        RolePrivilegeAssignment rolePrivilegeAssignment = rolePrivilegeAssignmentRepository.findByRoleAndPrivilegeId(role, privilegeId).orElseThrow(() -> new RuntimeException("Privilege not assigned by " + privilegeId));

        rolePrivilegeAssignmentRepository.delete(rolePrivilegeAssignment);

        return "Privilege removed from role";
    }

    public String removeMultiple(long roleId, PrivilegeIdsDTO privilegeIdsDTO) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException("Role not found when removing"));
        for (long privilegeId : privilegeIdsDTO.privilegeIds()) {
            this.findById(privilegeId).orElseThrow(() -> new PrivilegeNotFoundException("Privilege Not found while removing"));

            RolePrivilegeAssignment rolePrivilegeAssignment = rolePrivilegeAssignmentRepository.findByRoleAndPrivilegeId(role, privilegeId).orElseThrow(() -> new RuntimeException("Privilege not assigned by " + privilegeId));

            rolePrivilegeAssignmentRepository.delete(rolePrivilegeAssignment);
        }

        return "Privileges removed from role";
    }

    public Optional<Privilege> findById(long privilegeID) {
        return privileges.stream()
                .filter(privilege -> privilege.getId().equals(privilegeID))
                .findFirst();
    }

    public Optional<Privilege> findByHttpMethodAndEndpoint(String httpMethod, String endpoint) {
        return privileges.stream()
                .filter(privilege -> privilege.getHttpMethod().equalsIgnoreCase(httpMethod)
                        && privilege.getEndpoint().equals(endpoint))
                .findFirst();
    }

    public boolean existsByHttpMethodAndEndpoint(String httpMethod, String endpoint) {
        return privileges.stream()
                .anyMatch(privilege -> privilege.getHttpMethod().equalsIgnoreCase(httpMethod)
                        && privilege.getEndpoint().equals(endpoint));
    }

    public Page<Privilege> getAll(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Privilege> pagedPrivileges;

        if (startItem >= privileges.size()) {
            pagedPrivileges = List.of();
        } else {
            List<Privilege> privilegeList = new ArrayList<>(privileges);
            int toIndex = Math.min(startItem + pageSize, privilegeList.size());
            pagedPrivileges = privilegeList.subList(startItem, toIndex);
        }

        return new PageImpl<>(pagedPrivileges, pageable, privileges.size());
    }


}
