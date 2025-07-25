package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.BlockInfo;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.BlockedUserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Author: Md Jahid Hasan
 * Date: 2/4/25
 */
public interface BlockInfoRepository extends BaseRepository<BlockInfo, Long> {
    Page<BlockedUserProjection> findAllBy(Class<BlockedUserProjection> projection, Pageable pageable);

    List<BlockInfo> findAllByUnblockAtBeforeAndUserIsBlockedTrue(LocalDateTime now);

    Page<BlockedUserProjection> findAllByUserIsBlockedTrue(Class<BlockedUserProjection> projection, Pageable pageable);

    Optional<BlockInfo> findByUserAndDeletedAtIsNull(User user);

    List<BlockInfo> findAllByUnblockAtBeforeAndDeletedAtIsNullAndUserIsBlockedTrue(LocalDateTime now);
}
