package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u.id AS id, u.name AS name, u.email AS email, u.address AS address FROM User u WHERE u.deletedAt IS NULL")
    List<UserProjection> findAllProjection();

    @Query("SELECT u.id AS id, u.name AS name, u.email AS email, u.address AS address FROM User u WHERE u.id = :id AND u.deletedAt IS NULL")
    UserProjection findProjectionById(UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.deletedAt=CURRENT_TIMESTAMP() WHERE u.id = :id")
    void deleteById(UUID id);
}
