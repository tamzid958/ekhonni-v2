/**
 * Author: Rifat Shariar Sakil
 * Time: 8:38 PM
 * Date: 2/3/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.User;
import com.ekhonni.backend.model.WatchlistProduct;
import com.ekhonni.backend.projection.WatchlistProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WatchlistRepository extends JpaRepository<WatchlistProduct, Long> {
    Page<WatchlistProductProjection> findAllProjectionByUser(@Param("user") User user, Pageable pageable);

    void deleteByUserIdAndProductIdIn(UUID userId, List<Long> productIds);
    void deleteByProductIdIn(List<Long> productIds);
}
