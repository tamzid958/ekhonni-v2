package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends BaseRepository<Account, Long> {

    Optional<User> findUserById(Long id);

    Optional<Account> findByUserId(UUID userId);

    @Query("SELECT a FROM Account a WHERE a.user.role.name = 'SUPER_ADMIN'")
    Optional<Account> findSuperAdminAccount();
}
