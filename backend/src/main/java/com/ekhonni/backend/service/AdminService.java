package com.ekhonni.backend.service;

import com.ekhonni.backend.enums.Role;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author: Md Jahid Hasan
 * Date: 12/18/24
 */
@Service
@AllArgsConstructor
public class AdminService {

    AdminRepository adminRepository;

    @Transactional
    public void add(String email) {
        User user = adminRepository.findByEmail(email);
        user.setRole(Role.ADMIN);
    }

    @Transactional
    public void remove(String email) {
        User user = adminRepository.findByEmail(email);
        user.setRole(Role.USER);
    }
}
