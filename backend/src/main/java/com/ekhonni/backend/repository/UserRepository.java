package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.UserProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 */

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {

    @Query("SELECT u.id AS id, u.name AS name, u.email AS email, u.address AS address FROM User u WHERE u.deletedAt IS NULL")
    List<UserProjection> findAllProjection();

    @Query("SELECT u.id AS id, u.name AS name, u.email AS email, u.address AS address FROM User u WHERE u.id = :id AND u.deletedAt IS NULL")
    UserProjection findProjectionById(UUID id);


}
