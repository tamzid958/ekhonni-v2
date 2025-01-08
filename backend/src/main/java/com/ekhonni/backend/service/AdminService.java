package com.ekhonni.backend.service;

import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.UserProjection;
import com.ekhonni.backend.repository.AdminRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 * Date: 12/18/24
 */
@Service
public class AdminService extends BaseService<User, UUID> {

    AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        super(adminRepository);
        this.adminRepository = adminRepository;
    }

//    @Transactional
//    public void add(String email) {
//        User user = adminRepository.findByEmail(email);
//        user.setRole(Role.ADMIN);
//    }
//
//    @Transactional
//    public void remove(String email) {
//        User user = adminRepository.findByEmail(email);
//        user.setRole(Role.USER);
//    }

    public Page<UserProjection> getAllBlocked(Class<UserProjection> projection, Pageable pageable) {
        return adminRepository.findAllByBlockedAtIsNotNull(projection, pageable);
    }

    public void block(UUID id) {
        adminRepository.block(id);
    }


    public Page<UserProjection> getAllUser(Class<UserProjection> projection, Pageable pageable) {
        return adminRepository.findAllByDeletedAtIsNullAndBlockedAtIsNull(projection, pageable);
    }
}
