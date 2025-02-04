package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.BlockInfo;
import com.ekhonni.backend.projection.BlockedUserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Author: Md Jahid Hasan
 * Date: 2/4/25
 */
public interface BlockInfoRepository extends BaseRepository<BlockInfo, Long> {
    Page<BlockedUserProjection> findAllBy(Class<BlockedUserProjection> projection, Pageable pageable);
}
