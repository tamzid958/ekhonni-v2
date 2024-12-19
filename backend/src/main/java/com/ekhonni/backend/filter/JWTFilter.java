package com.ekhonni.backend.filter;

import com.ekhonni.backend.service.UserDetailsServiceImpl;
import com.ekhonni.backend.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Author: Md Jahid Hasan
 * Date: 12/13/24
 */

@Service
@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    JWTUtil jwtUtil;

    UserDetailsServiceImpl userDetailsServiceImpl;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);

        String blackListedToken = jwtUtil.getBlackListed(jwt);

        if (blackListedToken != null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
        }


        try {
            String email = jwtUtil.extractSubject(jwt);

            Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();

            if (email != null && currentAuth == null) {
                if (!jwtUtil.isExpired(jwt)) {
                    UserDetails user = userDetailsServiceImpl.loadUserByUsername(email);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token Expired");
                }
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            logger.error("JWT validation error: ", e);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid JWT Token");
        }

    }
}
