package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.UserDTO;
import com.ekhonni.backend.exception.UserNotFoundException;
import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.UserProjection;
import com.ekhonni.backend.repository.AccountRepository;
import com.ekhonni.backend.repository.BaseRepository;
import com.ekhonni.backend.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@Setter
public class UserService extends BaseService<User, UUID> {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public UserService(UserRepository userRepository, AccountRepository accountRepository) {
        super(userRepository);
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public UserProjection getById(UUID id) {
        return userRepository.findById(id, UserProjection.class)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserDTO create(UserDTO userDTO) {
        Account account = new Account(0.0, "Active");
        User user = new User(
                userDTO.name(),
                userDTO.email(),
                userDTO.password(),
                "USER",
                userDTO.phone(),
                userDTO.address(),
                account
        );
        userRepository.save(user);
        accountRepository.save(account);
        return userDTO;
    }

    public void createAll(List<UserDTO> userDTOs) {
        for (UserDTO userDTO : userDTOs) {
            Account account = new Account(0.0, "Active");
            User user = new User(
                    userDTO.name(),
                    userDTO.email(),
                    userDTO.password(),
                    "USER",
                    userDTO.phone(),
                    userDTO.address(),
                    account
            );
            userRepository.save(user);
            accountRepository.save(account);
        }

    }

    public void delete(UUID id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.softDelete(id);
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

}
