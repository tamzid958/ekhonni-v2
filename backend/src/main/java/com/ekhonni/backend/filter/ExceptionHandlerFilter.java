package com.ekhonni.backend.filter;

import com.ekhonni.backend.exception.ErrorResponse;
import com.ekhonni.backend.exception.InvalidJwtTokenException;
import com.ekhonni.backend.exception.NoResourceFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Author: Md Jahid Hasan
 * Date: 1/5/25
 */
@Component
@AllArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (NoResourceFoundException e) {
            handleException(response, e, HttpStatus.NOT_FOUND);
        } catch (InvalidJwtTokenException e) {
            handleException(response, e, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            handleException(response, e, HttpStatus.BAD_REQUEST);
        }
    }

    private void handleException(HttpServletResponse response, Exception e, HttpStatus status) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                LocalDateTime.now().toString()
        );

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
