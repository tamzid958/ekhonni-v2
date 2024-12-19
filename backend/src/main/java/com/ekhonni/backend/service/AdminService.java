package com.ekhonni.backend.service;

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
        user.setRole("ADMIN");
    }

    @Transactional
    public void remove(String email) {
        User user = adminRepository.findByEmail(email);
        user.setRole("USER");
    }
}
