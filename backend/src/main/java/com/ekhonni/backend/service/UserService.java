package com.ekhonni.backend.service;

import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.AccountRepository;
import com.ekhonni.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 */

@Service
@RequiredArgsConstructor
@Setter
public class UserService extends BaseService<User, UUID> {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

}