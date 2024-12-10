package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.AuthDTO;
import com.ekhonni.backend.dto.UserDTO;
import com.ekhonni.backend.enums.Role;
import com.ekhonni.backend.exception.UserAlreadyExistsException;
import com.ekhonni.backend.exception.UserNotFoundException;
import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.UserProjection;
import com.ekhonni.backend.repository.AccountRepository;
import com.ekhonni.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 */

@Service
@RequiredArgsConstructor
@Setter
public class UserService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;


    public List<UserProjection> getAll() {
        return userRepository.findAllProjection();
    }


    public UserProjection getById(UUID id) {
        if (userRepository.findById(id).isEmpty()) throw new UserNotFoundException();
        return userRepository.findProjectionById(id);
    }


    public UserDTO create(UserDTO userDTO) {
        if (userRepository.findUserByEmail(userDTO.email()).isPresent()) throw new UserAlreadyExistsException();

        Account account = new Account(0.0, "Active");

        User user = new User(
                userDTO.name(),
                userDTO.email(),
                userDTO.password(),
                Role.USER,
                userDTO.phone(),
                userDTO.address(),
                account
        );
        userRepository.save(user);
        accountRepository.save(account);

        return userDTO;
    }


    public void delete(UUID id) {
        if (userRepository.findById(id).isEmpty()) throw new UserNotFoundException();
        userRepository.softDeleteById(id);
    }


    @Transactional
    public UserDTO update(UUID id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if (userDTO.name() != null && !userDTO.name().isBlank()) {
            user.setName(userDTO.name());
        }
        if (userDTO.email() != null && !userDTO.email().isBlank()) {
            user.setEmail(userDTO.email());
        }
        if (userDTO.password() != null && !userDTO.password().isBlank()) {
            user.setPassword(userDTO.password());
        }
        if (userDTO.phone() != null && !userDTO.phone().isBlank()) {
            user.setPhone(userDTO.phone());
        }
        if (userDTO.address() != null && !userDTO.address().isBlank()) {
            user.setAddress(userDTO.address());
        }

        return userDTO;
    }


    public void signIn(AuthDTO authDTO) {
    }
}
