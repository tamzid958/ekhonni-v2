package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.BlockInfo;
import com.ekhonni.backend.projection.BlockedUserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Author: Md Jahid Hasan
 * Date: 2/4/25
 */
public interface BlockInfoRepository extends BaseRepository<BlockInfo, Long> {
    Page<BlockedUserProjection> findAllBy(Class<BlockedUserProjection> projection, Pageable pageable);

    List<BlockInfo> findAllByUnblockAtBeforeAndUserIsBlockedTrue(LocalDateTime now);
}
