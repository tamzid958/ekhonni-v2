package com.ekhonni.backend.repository;

import com.ekhonni.backend.projection.DetailedUserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Author: Md Jahid Hasan
 * Date: 12/18/24
 */
@Repository
public interface AdminRepository extends UserRepository {
    Page<DetailedUserProjection> findAllByIsBlockedIsTrue(Class<DetailedUserProjection> projection, Pageable pageable);

    Page<DetailedUserProjection> findAllByDeletedAtIsNullAndIsBlockedIsFalseAndVerifiedIsTrue(Class<DetailedUserProjection> projection, Pageable pageable);

    Page<DetailedUserProjection> findAllByNameContainingIgnoreCaseOrEmail(Class<DetailedUserProjection> projection, Pageable pageable, String name, String email);
}
