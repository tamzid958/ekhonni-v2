package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.UserDTO;
import com.ekhonni.backend.exception.UserNotFoundException;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.UserProjection;
import com.ekhonni.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Setter
public class UserService {
    private final UserRepository userRepository;


    public List<UserProjection> getAll() {
        return userRepository.findAllProjection();
    }

    public UserProjection getById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        return userRepository.findProjectionById(id);
    }

    public UserDTO create(UserDTO userDTO) {
        User user = new User(userDTO.name(),
                userDTO.email(),
                userDTO.password(),
                "user",
                userDTO.phone(),
                userDTO.address());
        userRepository.save(user);
        return userDTO;
    }

    public void delete(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        userRepository.deleteById(id);
    }

    @Transactional
    public UserDTO updateUserInfo(UUID id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        if (userDTO.name() != null && !userDTO.name().isBlank()) {
            user.setName(userDTO.name());
        }
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        user.setPhone(userDTO.phone());
        user.setAddress(userDTO.address());

        return userDTO;
    }


}
