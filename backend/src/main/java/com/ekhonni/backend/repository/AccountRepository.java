package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends BaseRepository<Account, Long> {

    Optional<User> findUserById(Long id);

    @Query("SELECT u.account FROM User u WHERE u.role.name = :roleName")
    Optional<Account> findByRoleName(String roleName);

}
