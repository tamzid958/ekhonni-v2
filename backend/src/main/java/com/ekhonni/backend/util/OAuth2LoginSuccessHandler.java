package com.ekhonni.backend.util;

import com.ekhonni.backend.model.AuthClaim;
import com.ekhonni.backend.model.AuthToken;
import com.ekhonni.backend.model.RefreshToken;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.RefreshTokenRepository;
import com.ekhonni.backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Author: Md Jahid Hasan
 * Date: 1/29/25
 */
@Component
@AllArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenUtil tokenUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JsonUtil jsonUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oauth2User.getAttributes();
        String email = (String) attributes.get("email");

        User user = userRepository.findByEmail(email);

        String accessToken = tokenUtil.generateJwtAccessToken(email);

        RefreshToken refreshToken = tokenUtil.generateRefreshToken();

        refreshTokenRepository.save(refreshToken);

        user.setRefreshToken(refreshToken);

        AuthClaim authClaim = AuthClaim
                .builder()
                .id(user.getId())
                .authToken(new AuthToken(accessToken, refreshToken.getValue()))
                .build();

        response.setContentType("application/json");
        String jsonResponse = jsonUtil.getObjectMapper().writeValueAsString(authClaim);
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }

}

