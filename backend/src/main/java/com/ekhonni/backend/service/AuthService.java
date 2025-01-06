package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.AuthDTO;
import com.ekhonni.backend.dto.UserDTO;
import com.ekhonni.backend.exception.RoleNotFoundException;
import com.ekhonni.backend.exception.UserAlreadyExistsException;
import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.AuthToken;
import com.ekhonni.backend.model.Role;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.AccountRepository;
import com.ekhonni.backend.repository.RoleRepository;
import com.ekhonni.backend.repository.UserRepository;
import com.ekhonni.backend.util.JWTUtil;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author: Md Jahid Hasan
 * Date: 12/12/24
 */

@Service
@AllArgsConstructor
@Setter
public class AuthService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final JWTUtil jwtUtil;

    @Transactional
    public String create(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.email()) != null) throw new UserAlreadyExistsException();

        Account account = new Account(0.0, "Active");

        Role userRole = roleRepository.findByName("USER").orElseThrow(RoleNotFoundException::new);

        User user = new User(
                userDTO.name(),
                userDTO.email(),
                passwordEncoder.encode(userDTO.password()),
                userDTO.phone(),
                userDTO.address(),
                userRole,
                account,
                null,
                null
        );

        accountRepository.save(account);
        userRepository.save(user);

        return "User Successfully registered";
    }


    public AuthToken signIn(AuthDTO authDTO) {
        String email = authDTO.email();
        String password = authDTO.password();

        if (userRepository.findByEmail(email) == null) throw new BadCredentialsException("Bad credentials");

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);

        Authentication authenticatedUser = authenticationManager.authenticate(authentication);

        String accessToken = jwtUtil.generateAccessToken(authenticatedUser);

        String refreshToken = jwtUtil.generateRefreshToken(authenticatedUser);

//        userRepository.findByEmail(email).setRefreshToken(new RefreshToken(refreshToken));

        return new AuthToken(accessToken, refreshToken);
    }


}
