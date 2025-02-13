package com.ekhonni.backend.filter;

import com.ekhonni.backend.exception.user.InvalidJwtTokenException;
import com.ekhonni.backend.service.UserDetailsServiceImpl;
import com.ekhonni.backend.util.TokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Author: Md Jahid Hasan
 * Date: 12/13/24
 */

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final TokenUtil tokenUtil;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (!isBearerToken(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = extractToken(authHeader);

        try {
            String email = tokenUtil.extractSubject(token);
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                validateAndAuthenticateToken(request, response, token, email);
            }

        } catch (Exception e) {
            throw new InvalidJwtTokenException();
        }

        filterChain.doFilter(request, response);
    }

    private boolean isBearerToken(String authHeader) {
        return authHeader != null && authHeader.startsWith("Bearer ");
    }

    private String extractToken(String authHeader) {
        return authHeader.substring(7);
    }

    private void validateAndAuthenticateToken(HttpServletRequest request, HttpServletResponse response, String token, String email) throws IOException {
        if (tokenUtil.isAccessTokenExpired(token)) {
            throw new InvalidJwtTokenException();
        }

        UserDetails user = userDetailsServiceImpl.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

}
