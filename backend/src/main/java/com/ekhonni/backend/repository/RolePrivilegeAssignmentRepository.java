package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Role;
import com.ekhonni.backend.model.RolePrivilegeAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Author: Md Jahid Hasan
 * Date: 12/30/24
 */
@Repository
public interface RolePrivilegeAssignmentRepository extends BaseRepository<RolePrivilegeAssignment, Long> {
    Page<RolePrivilegeAssignment> findAllByRoleId(long roleId, Pageable pageable);

    List<RolePrivilegeAssignment> findAllByRoleId(long roleId);

    boolean existsByRoleAndPrivilegeId(Role role, long privilegeId);

    Optional<RolePrivilegeAssignment> findByRoleAndPrivilegeId(Role role, long privilegeId);
}
