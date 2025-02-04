package com.ekhonni.backend.service;

import com.cloudinary.Api;
import com.ekhonni.backend.dto.EmailDTO;
import com.ekhonni.backend.dto.PasswordDTO;
import com.ekhonni.backend.dto.ProfileImageDTO;
import com.ekhonni.backend.dto.RefreshTokenDTO;
import com.ekhonni.backend.exception.UserNotFoundException;
import com.ekhonni.backend.model.AuthToken;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.model.VerificationToken;
import com.ekhonni.backend.repository.UserRepository;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.util.CloudinaryImageUploadUtil;
import com.ekhonni.backend.util.TokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 */


@Service
public class UserService extends BaseService<User, UUID> {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenUtil tokenUtil;
    private final CloudinaryImageUploadUtil cloudinaryImageUploadUtil;
    private final EmailChangeService emailChangeService;

    @Value("${profile.image.upload.dir}")
    String PROFILE_IMAGE_UPLOAD_DIR;

    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, TokenUtil tokenUtil, CloudinaryImageUploadUtil cloudinaryImageUploadUtil, EmailChangeService emailChangeService) {
        super(userRepository);
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtil = tokenUtil;
        this.cloudinaryImageUploadUtil = cloudinaryImageUploadUtil;
        this.emailChangeService = emailChangeService;
    }

    public String updateEmailRequest(UUID id, EmailDTO emailDTO){
        User user = userRepository.findById(id)
                .orElseThrow( () -> new UserNotFoundException("User not Found"));
        emailChangeService.request(user, emailDTO);
        return "send";
    }

    @Transactional
    public ApiResponse<?> updateEmail(String token) {
        return emailChangeService.verifyAndUpdate(token);
    }

    @Transactional
    @Modifying
    public String updatePassword(UUID id, PasswordDTO passwordDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found when updating password"));

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), passwordDTO.currentPassword());

        Authentication authenticatedUser = authenticationManager.authenticate(authentication);

        user.setPassword(passwordEncoder.encode(passwordDTO.newPassword()));
        return "Password Updated";
    }

    public boolean isActive(UUID id) {
        return userRepository.existsByIdAndDeletedAtIsNullAndBlockedAtIsNull(id);
    }

    public boolean isSuperAdmin(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found when checking role"));
        String s = user.getRole().getName();
        return s.equals("SUPER_ADMIN");
    }

    public boolean isActive(String email) {
        return userRepository.existsByEmailAndDeletedAtIsNullAndBlockedAtIsNull(email);
    }

    public AuthToken getNewAccessToken(RefreshTokenDTO refreshTokenDTO) {
        String refreshToken = refreshTokenDTO.refreshToken();

        if (!tokenUtil.isRefreshTokenValid(refreshToken)) throw new RuntimeException("Invalid Refresh Token");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String accessToken = tokenUtil.generateJwtAccessToken(user.getEmail());

        return new AuthToken(accessToken, refreshToken);
    }

    @Transactional
    @Modifying
    public String upload(UUID id, ProfileImageDTO profileImageDTO) throws IOException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found while uploading"));

        MultipartFile profileImage = profileImageDTO.image();

        String profileImagePath = cloudinaryImageUploadUtil.uploadImage(profileImageDTO.image());

        user.setProfileImage(profileImagePath);

        return "Profile image uploaded successfully";
    }
}