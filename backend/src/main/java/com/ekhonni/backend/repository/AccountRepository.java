package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.projection.AccountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Modifying
    @Query("DELETE FROM Account a WHERE a.id = :id")
    void deleteAccountById(@Param("id") Long id);

    @Query("SELECT a FROM Account a WHERE a.deletedAt IS NULL")
    List<AccountProjection> getAll();

    @Query("SELECT a FROM Account a WHERE a.deletedAt IS NOT NULL")
    List<AccountProjection> getAllDeleted();

    @Query("SELECT a FROM Account a")
    List<AccountProjection> getAllIncludingDeleted();

}
