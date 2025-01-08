package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Privilege;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Author: Md Jahid Hasan
 * Date: 12/29/24
 */
@Repository
public interface PrivilegeRepository extends BaseRepository<Privilege, Long> {
    Optional<Privilege> findByHttpMethodAndEndpoint(String httpMethod, String endpoint);

    boolean existsByHttpMethodAndEndpoint(String s, String endpoint);
//    Page<Privilege> getAllByRole(long roleId, Pageable pageable);
}
