package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.EmailDTO;
import com.ekhonni.backend.dto.PasswordDTO;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 */


@Service
public class UserService extends BaseService<User, UUID> {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public String updateEmail(UUID id, EmailDTO emailDTO) {
        // email to be verified
        this.update(id, emailDTO);
        return "Email Updated";
    }

    @Transactional
    public String updatePassword(UUID id, PasswordDTO passwordDTO) {
        return "To be implemented";
    }
}