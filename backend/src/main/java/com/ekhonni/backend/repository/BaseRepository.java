package com.ekhonni.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author: Md Jahid Hasan
 * Date: 12/8/24
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
    @Modifying
    @Transactional
    @Query("UPDATE #{#entityName} u SET u.deletedAt=CURRENT_TIMESTAMP() WHERE u.id = :id")
    void softDeleteById(ID id);

}
