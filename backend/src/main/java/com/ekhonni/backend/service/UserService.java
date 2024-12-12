package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.LoginDTO;
import com.ekhonni.backend.dto.UserDTO;
import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.UserProjection;
import com.ekhonni.backend.repository.AccountRepository;
import com.ekhonni.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Setter
public class UserService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public List<UserProjection> getAll() {
        return userRepository.findAllProjection();
    }

    public UserProjection getById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
        return userRepository.findProjectionById(id);
    }

    public UserDTO create(UserDTO userDTO) {
        User user = new User(
                userDTO.name(),
                userDTO.email(),
                passwordEncoder.encode(userDTO.password()),
                "USER",
                userDTO.phone(),
                userDTO.address()
        );
        userRepository.save(user);

        Account account = new Account(user, 0.0, "active");
        accountRepository.save(account);

        return userDTO;
    }


    public void delete(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
        userRepository.deleteById(id);
    }

    @Transactional
    public UserDTO updateUserInfo(UUID id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
        if (userDTO.name() != null && !userDTO.name().isBlank()) {
            user.setName(userDTO.name());
        }
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        user.setPhone(userDTO.phone());
        user.setAddress(userDTO.address());

        return userDTO;
    }

    public String authenticateUser(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.email(),
                        loginDTO.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "Login Successful";
    }
}
