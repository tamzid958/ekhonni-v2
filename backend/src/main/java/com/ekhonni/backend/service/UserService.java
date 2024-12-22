package com.ekhonni.backend.service;

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

    public UserService(UserRepository userRepository) {
        super(userRepository);
    }


    @Transactional
    public String updateEmail(UUID id, String email) {
        return "To be implemented";
//        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
//        user.setEmail(email);
//        return "Email Updated";
    }

    @Transactional
    public String updatePassword(UUID id, String password) {
        return "To be implemented";
//        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
//        user.setPassword(passwordEncoder.encode(password));
//        return "Password Updated";
    }
}