package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Role;
import com.ekhonni.backend.model.User;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 */

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {

    User findByEmail(String email);

    boolean existsByRole(Role role);

    boolean existsByIdAndDeletedAtIsNullAndBlockedAtIsNull(UUID id);

    boolean existsByEmailAndDeletedAtIsNullAndBlockedAtIsNull(String email);
}
