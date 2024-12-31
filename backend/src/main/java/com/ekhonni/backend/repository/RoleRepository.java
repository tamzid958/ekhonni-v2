package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Author: Md Jahid Hasan
 * Date: 12/29/24
 */
@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {
    boolean existsByName(String roleName);

    Optional<Role> findByName(String user);
}
