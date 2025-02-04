package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.UserBlockDTO;
import com.ekhonni.backend.exception.user.UserNotFoundException;
import com.ekhonni.backend.model.BlockInfo;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.BlockedUserProjection;
import com.ekhonni.backend.projection.DetailedUserProjection;
import com.ekhonni.backend.repository.AdminRepository;
import com.ekhonni.backend.repository.BlockInfoRepository;
import com.ekhonni.backend.util.AuthUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 * Date: 12/18/24
 */
@Service
public class AdminService extends BaseService<User, UUID> {

    AdminRepository adminRepository;
    UserService userService;
    BlockInfoRepository blockInfoRepository;

    public AdminService(AdminRepository adminRepository, UserService userService, BlockInfoRepository blockInfoRepository) {
        super(adminRepository);
        this.blockInfoRepository = blockInfoRepository;
        this.adminRepository = adminRepository;
        this.userService = userService;
    }


    public Page<BlockedUserProjection> getAllBlocked(Class<BlockedUserProjection> projection, Pageable pageable) {
        return blockInfoRepository.findAllBy(projection, pageable);
//        return adminRepository.findAllByIsBlockedIsTrue(projection, pageable);
    }


    @Transactional
    @Modifying
    public void block(UserBlockDTO userBlockDTO) {
        User user = adminRepository.findById(userBlockDTO.id()).orElseThrow(() -> new UserNotFoundException("User not found with id " + userBlockDTO.id()));
        BlockInfo blockInfo = new BlockInfo(
                user,
                AuthUtil.getAuthenticatedUser(),
                userBlockDTO.reason(),
                userBlockDTO.blockPolicy(),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(userBlockDTO.blockPolicy().getDays())
        );
        blockInfoRepository.save(blockInfo);
        user.setBlocked(true);
    }

    @Transactional
    @Modifying
    public void unblock(UUID id) {
        User user = adminRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
        BlockInfo blockInfo = blockInfoRepository.findByUserAndDeletedAtIsNull(user);
        blockInfo.setUnblockAt(LocalDateTime.now());
        blockInfoRepository.softDelete(blockInfo.getId());
        user.setBlocked(false);
    }

    public Page<DetailedUserProjection> getAllUserByNameOrEmail(Class<DetailedUserProjection> projection, Pageable pageable, String name, String email) {
        return adminRepository.findAllByNameContainingIgnoreCaseOrEmail(projection, pageable, name, email);
    }

    public Page<DetailedUserProjection> getAllActive(Class<DetailedUserProjection> projection, Pageable pageable) {
        return adminRepository.findAllByDeletedAtIsNullAndIsBlockedIsFalseAndVerifiedIsTrue(projection, pageable);
    }

}
