package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u.id AS id, u.name AS name, u.email AS email, u.address AS address FROM User u")
    List<UserProjection> findAllProjection();

    @Query("SELECT u.id AS id, u.name AS name, u.email AS email, u.address AS address FROM User u WHERE u.id = :id")
    UserProjection findProjectionById(UUID id);

    Optional<User> findByEmail(String email);
}
