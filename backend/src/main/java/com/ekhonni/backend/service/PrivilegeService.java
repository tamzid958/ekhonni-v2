package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.PrivilegeDTO;
import com.ekhonni.backend.exception.PrivilegeNotFoundException;
import com.ekhonni.backend.exception.RoleNotFoundException;
import com.ekhonni.backend.model.Privilege;
import com.ekhonni.backend.model.Role;
import com.ekhonni.backend.model.RolePrivilegeAssignment;
import com.ekhonni.backend.repository.PrivilegeRepository;
import com.ekhonni.backend.repository.RolePrivilegeAssignmentRepository;
import com.ekhonni.backend.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Author: Md Jahid Hasan
 * Date: 12/29/24
 */
@Service
public class PrivilegeService extends BaseService<Privilege, Long> {

    PrivilegeRepository privilegeRepository;
    RoleRepository roleRepository;
    RolePrivilegeAssignmentRepository rolePrivilegeAssignmentRepository;

    public PrivilegeService(PrivilegeRepository privilegeRepository, RoleRepository roleRepository, RolePrivilegeAssignmentRepository rolePrivilegeAssignmentRepository) {
        super(privilegeRepository);
        this.privilegeRepository = privilegeRepository;
        this.roleRepository = roleRepository;
        this.rolePrivilegeAssignmentRepository = rolePrivilegeAssignmentRepository;
    }

    @Transactional
    public void add(PrivilegeDTO privilegeDTO) {

        if (!privilegeRepository.existsByHttpMethodAndEndpoint(privilegeDTO.httpMethod(), privilegeDTO.endpoint())) {
            Privilege privilege = new Privilege(
                    privilegeDTO.name(),
                    privilegeDTO.description(),
                    privilegeDTO.httpMethod(),
                    privilegeDTO.endpoint()
            );

            privilegeRepository.save(privilege);

            assignPrivilegeToSuperAdmin(privilege);
        }

    }

    @Transactional
    public void addMultiple(List<PrivilegeDTO> privilegeDTOList) {

        for (PrivilegeDTO privilegeDTO : privilegeDTOList) {
            this.add(privilegeDTO);
        }

    }

    private void assignPrivilegeToSuperAdmin(Privilege privilege) {
        Role roleAdmin = roleRepository.findByName("SUPER_ADMIN").orElseThrow(RoleNotFoundException::new);

        RolePrivilegeAssignment rolePrivilegeAssignment = new RolePrivilegeAssignment(roleAdmin, privilege);

        rolePrivilegeAssignmentRepository.save(rolePrivilegeAssignment);
    }


    public String assign(long roleId, long privilegeId) {
        Role role = roleRepository.findById(roleId).orElseThrow(RoleNotFoundException::new);
        Privilege privilege = privilegeRepository.findById(privilegeId).orElseThrow(PrivilegeNotFoundException::new);

        RolePrivilegeAssignment rolePrivilegeAssignment = new RolePrivilegeAssignment(role, privilege);

        rolePrivilegeAssignmentRepository.save(rolePrivilegeAssignment);

        return "Privilege assigned to role";
    }

    public Page<Privilege> getAllOfRole(long roleId, Pageable pageable) {
        Page<RolePrivilegeAssignment> rolePrivilegeAssignments = rolePrivilegeAssignmentRepository.findAllByRoleId(roleId, pageable);

        return rolePrivilegeAssignments.map(RolePrivilegeAssignment::getPrivilege);
    }

    public List<Privilege> getAllOfRole(Role role) {
        long roleId = role.getId();

        List<RolePrivilegeAssignment> rolePrivilegeAssignments = rolePrivilegeAssignmentRepository.findAllByRoleId(roleId);

        return rolePrivilegeAssignments.stream()
                .map(RolePrivilegeAssignment::getPrivilege)
                .collect(Collectors.toList());
    }

    public Privilege getByHttpMethodAndEndpoint(String httpMethod, String endpoint) {
        return privilegeRepository.findByHttpMethodAndEndpoint(httpMethod, endpoint).orElseThrow(PrivilegeNotFoundException::new);
    }
}
