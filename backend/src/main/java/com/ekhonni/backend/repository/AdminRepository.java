package com.ekhonni.backend.repository;

import com.ekhonni.backend.projection.DetailedUserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 * Date: 12/18/24
 */
@Repository
public interface AdminRepository extends UserRepository {
    Page<DetailedUserProjection> findAllByBlockedAtIsNotNull(Class<DetailedUserProjection> projection, Pageable pageable);

    Page<DetailedUserProjection> findAllByDeletedAtIsNullAndBlockedAtIsNull(Class<DetailedUserProjection> projection, Pageable pageable);

    Page<DetailedUserProjection> findAllByNameContainingIgnoreCaseOrEmailAndDeletedAtIsNullAndBlockedAtIsNull(Class<DetailedUserProjection> projection, Pageable pageable, String name, String email);


    @Modifying
    @Transactional
    @Query("UPDATE User e SET e.blockedAt=CURRENT_TIMESTAMP() WHERE e.id = :id")
    void block(UUID id);

}
