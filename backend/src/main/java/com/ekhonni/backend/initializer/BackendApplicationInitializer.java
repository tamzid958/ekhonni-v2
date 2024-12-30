package com.ekhonni.backend.initializer;

import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.Role;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.AccountRepository;
import com.ekhonni.backend.repository.RoleRepository;
import com.ekhonni.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Author: Md Jahid Hasan
 * Date: 12/23/24
 */
@Service
@AllArgsConstructor
public class BackendApplicationInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    @Value("${spring.security.admin.email}")
    private String adminEmail;
    @Value("${spring.security.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {

        if (!roleRepository.existsByName("SUPER_ADMIN")) {
            Role superAdmin = new Role("SUPER_ADMIN", "An Admin with highest level of privilege");
            roleRepository.save(superAdmin);

            if (!userRepository.existsByRole(superAdmin)) {
                Account account = new Account(0.0, "Active");
                User user = new User(
                        "Md Jahid Hasan",
                        adminEmail,
                        passwordEncoder.encode(adminPassword),
                        "01710108965",
                        "Shafipur, Kaliakair, Gazipur",
                        superAdmin,
                        account,
                        null
                );

                accountRepository.save(account);
                userRepository.save(user);

            }
        }


    }
}
