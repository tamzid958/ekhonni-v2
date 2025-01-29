package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.AuthDTO;
import com.ekhonni.backend.dto.UserDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.exception.EmailNotVerifiedException;
import com.ekhonni.backend.exception.RoleNotFoundException;
import com.ekhonni.backend.exception.UserAlreadyExistsException;
import com.ekhonni.backend.model.*;
import com.ekhonni.backend.repository.AccountRepository;
import com.ekhonni.backend.repository.RefreshTokenRepository;
import com.ekhonni.backend.repository.RoleRepository;
import com.ekhonni.backend.repository.UserRepository;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.util.TokenUtil;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.Modifying;
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
    private final TokenUtil tokenUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final EmailVerificationService emailVerificationService;

    @Transactional
    public ApiResponse<?> create(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.email()) != null) throw new UserAlreadyExistsException();

        Account account = new Account(0.0, "Active");

        Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new RoleNotFoundException("Role not found while creating"));

        User user = new User(
                userDTO.name(),
                userDTO.email(),
                passwordEncoder.encode(userDTO.password()),
                userDTO.phone(),
                userDTO.address(),
                userRole,
                account,
                null,
                null,
                null,
                false
        );

        accountRepository.save(account);
        userRepository.save(user);

        emailVerificationService.send(user);

        String responseMessage = "Sign up successful! Please verify your email to sign in";
        return new ApiResponse<>(HTTPStatus.OK, responseMessage);
    }


    @Transactional
    @Modifying
    public AuthClaim signIn(AuthDTO authDTO) {
        String email = authDTO.email();
        String password = authDTO.password();
        User user = userRepository.findByEmail(email);

        if (user == null)
            throw new BadCredentialsException("Bad credentials");

        if (!user.isVerified()) {
            throw new EmailNotVerifiedException("Email not verified. Please verify your email to sign in.");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);

        Authentication authenticatedUser = authenticationManager.authenticate(authentication);

        String accessToken = tokenUtil.generateJwtAccessToken(email);

        RefreshToken refreshToken = tokenUtil.generateRefreshToken();

        refreshTokenRepository.save(refreshToken);

        user.setRefreshToken(refreshToken);

        return AuthClaim
                .builder()
                .id(user.getId())
                .role(user.getRole().getName())
                .authToken(new AuthToken(accessToken, refreshToken.getValue()))
                .build();
    }


}
