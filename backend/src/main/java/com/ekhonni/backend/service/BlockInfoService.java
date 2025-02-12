package com.ekhonni.backend.service;

import com.ekhonni.backend.model.BlockInfo;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.BlockedUserProjection;
import com.ekhonni.backend.repository.BlockInfoRepository;
import com.ekhonni.backend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Author: Md Jahid Hasan
 * Date: 2/4/25
 */
@Service
public class BlockInfoService extends BaseService<BlockInfo, Long> {

    private final BlockInfoRepository blockInfoRepository;
    private final UserRepository userRepository;

    public BlockInfoService(BlockInfoRepository blockInfoRepository, UserRepository userRepository) {
        super(blockInfoRepository);
        this.blockInfoRepository = blockInfoRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Modifying
    @Scheduled(fixedRate = 86400000)
    public void unblockUsers() {

        List<BlockInfo> blockInfos = blockInfoRepository.findAllByUnblockAtBeforeAndDeletedAtIsNullAndUserIsBlockedTrue(LocalDateTime.now());

        for (BlockInfo blockInfo : blockInfos) {

            User user = blockInfo.getUser();

            user.setBlocked(false);

            this.softDelete(blockInfo.getId());
        }
    }

    public Page<BlockedUserProjection> getAllBlocked(Class<BlockedUserProjection> projection, Pageable pageable) {
        return blockInfoRepository.findAllByUserIsBlockedTrue(projection, pageable);
    }
}
