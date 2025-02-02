package com.ekhonni.backend.service;

import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.DetailedUserProjection;
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

    public Page<DetailedUserProjection> getAllBlocked(Class<DetailedUserProjection> projection, Pageable pageable) {
        return adminRepository.findAllByBlockedAtIsNotNull(projection, pageable);
    }

    public void block(UUID id) {
        adminRepository.block(id);
    }


    public Page<DetailedUserProjection> getAllUser(Class<DetailedUserProjection> projection, Pageable pageable) {
        return adminRepository.findAllByDeletedAtIsNullAndBlockedAtIsNull(projection, pageable);
    }

    public Page<DetailedUserProjection> getAllUserByNameOrEmail(Class<DetailedUserProjection> projection, Pageable pageable, String name, String email) {
        return adminRepository.findAllByNameContainingIgnoreCaseOrEmailAndDeletedAtIsNullAndBlockedAtIsNull(projection, pageable, name, email);
    }


}
