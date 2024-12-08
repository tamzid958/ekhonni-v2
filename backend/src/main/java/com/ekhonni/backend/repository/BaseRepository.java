package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Author: Md Jahid Hasan
 * Date: 12/8/24
 */
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
    @Override
    @Query("Update T as e SET e.deletedAt=CURRENT_TIMESTAMP WHERE e.id = :id")
    void deleteById(ID id);

}
