package com.ekhonni.backend.service;

import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.UserProjection;
import com.ekhonni.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service

public record UserService(UserRepository userRepository) {


    public List<UserProjection> getAll() {
        return userRepository.findAllProjection();
    }

    public UserProjection getById(UUID id) {
        return userRepository.findprojectionById(id);
    }

    public User create(User user) {

        return userRepository.save(user);
    }

    public void delete(UUID id) {
//        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
        userRepository.deleteById(id);
    }
}
