package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.EmailDTO;
import com.ekhonni.backend.dto.PasswordDTO;
import com.ekhonni.backend.exception.UserNotFoundException;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.UserRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 */


@Service
public class UserService extends BaseService<User, UUID> {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public String updateEmail(UUID id, EmailDTO emailDTO) {
        // email to be verified
        this.update(id, emailDTO);
        return "Email Updated";
    }

    @Transactional
    @Modifying
    public String updatePassword(UUID id, PasswordDTO passwordDTO) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), passwordDTO.currentPassword());

        Authentication authenticatedUser = authenticationManager.authenticate(authentication);

        user.setPassword(passwordEncoder.encode(passwordDTO.newPassword()));
        return "Password Updated";
    }

    public boolean isActive(UUID id) {
        return userRepository.existsByIdAndDeletedAtIsNullAndBlockedAtIsNull(id);
    }

    public boolean isSuperAdmin(UUID id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        String s = user.getRole().getName();
        boolean b = s.equals("SUPER_ADMIN");
        return b;
    }

    public boolean isActive(String email) {
        return userRepository.existsByEmailAndDeletedAtIsNullAndBlockedAtIsNull(email);
    }
}