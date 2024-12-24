package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.AuthDTO;
import com.ekhonni.backend.dto.UserDTO;
import com.ekhonni.backend.enums.Role;
import com.ekhonni.backend.exception.UserAlreadyExistsException;
import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.model.VerificationToken;
import com.ekhonni.backend.repository.AccountRepository;
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

/**
 * Author: Md Jahid Hasan
 * Co-Author: Safayet Rafi
 * Date: 23/12/24
 */

@Service
@AllArgsConstructor
@Setter
public class AuthService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final VerificationTokenService verificationTokenService;
    private final EmailService emailService;

    public String create(UserDTO userRegDTO) {
        if (userRepository.findByEmail(userRegDTO.email()) != null) throw new UserAlreadyExistsException();

        Account account = new Account(0.0, "Active");

        User user = new User(
                userRegDTO.name(),
                userRegDTO.email(),
                passwordEncoder.encode(userRegDTO.password()),
                Role.USER,
                userRegDTO.phone(),
                userRegDTO.address(),
                false,
                account
        );

        accountRepository.save(account);
        userRepository.save(user);

        VerificationToken verificationToken = verificationTokenService.create(user);
        emailService.sendVerificationEmail(user.getEmail(), verificationToken.getToken());

        return "Sign up successful! Please verify your email to sign in";
    }


    public String signIn(AuthDTO authDTO) {
        String email = authDTO.email();
        String password = authDTO.password();
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new BadCredentialsException("Bad Credentials");
        }

        if (!user.isVerified()) {
            throw new RuntimeException("Email not verified. Please verify your email to sign in.");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);

        Authentication authenticated = authenticationManager.authenticate(authentication);

        return jwtUtil.generate(authenticated);
    }
}
