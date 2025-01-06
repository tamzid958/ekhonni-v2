package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 */

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {

    User findByEmail(String email);
    @Modifying
    @Transactional
    @Query("""
                DELETE FROM User u
                WHERE u.id IN :userIds
            """)
    void deleteUsersByIds(List<UUID> userIds);

}
