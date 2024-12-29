package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.AccountProjection;
import com.ekhonni.backend.projection.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends BaseRepository<Account, Long> {
    //    @Query("SELECT u.id AS id, u.name AS name, u.email AS email, u.address AS address FROM User u WHERE u.deletedAt IS NULL")

    @Query("""
            SELECT u.id AS id, u.name AS name, u.email AS email, u.address AS address
            FROM User u JOIN u.account a
            WHERE a.id = :id
            """)
    UserProjection findUser(Long id);
}
