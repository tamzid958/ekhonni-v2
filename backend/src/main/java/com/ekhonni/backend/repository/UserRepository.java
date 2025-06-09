package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Role;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 */

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {

    User findByEmail(String email);

    boolean existsByRole(Role role);

    boolean existsByIdAndDeletedAtIsNullAndIsBlockedIsFalse(UUID id);

    boolean existsByEmailAndDeletedAtIsNullAndIsBlockedIsFalse(String email);

    Page<UserProjection> getAllByRole(Role role, Class<UserProjection> userProjectionClass, Pageable pageable);

    @Modifying
    @Transactional
    void deleteByIdIn(List<UUID> userIds);

}
