package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.AuthDTO;
import com.ekhonni.backend.dto.UserDTO;
import com.ekhonni.backend.exception.UserAlreadyExistsException;
import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.AccountRepository;
import com.ekhonni.backend.repository.UserRepository;
import com.ekhonni.backend.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Author: Md Jahid Hasan
 * Date: 12/12/24
 */

@Service
@RequiredArgsConstructor
@Setter
public class AuthService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public String create(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.email()) != null) throw new UserAlreadyExistsException();

        Account account = new Account(0.0, "Active");

        User user = new User(
                userDTO.name(),
                userDTO.email(),
                passwordEncoder.encode(userDTO.password()),
                userDTO.name().equals("Jahid Hasan") ? "ADMIN" : "USER",
                userDTO.phone(),
                userDTO.address(),
                account
        );

        accountRepository.save(account);
        userRepository.save(user);

        return jwtUtil.generate(userDTO.email());
    }


    public String signIn(AuthDTO authDTO) {
        String email = authDTO.email();
        String password = authDTO.password();

        if (userRepository.findByEmail(email) == null) throw new BadCredentialsException("Bad credentials");

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);

        Authentication authenticated = authenticationManager.authenticate(authentication);

        return jwtUtil.generate(authenticated);
    }
}
