package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Privilege;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Author: Md Jahid Hasan
 * Date: 12/29/24
 */
public interface PrivilegeRepository extends BaseRepository<Privilege, Long> {
    Page<Privilege> getAllByRole(long roleId, Pageable pageable);
}
