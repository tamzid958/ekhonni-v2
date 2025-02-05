package com.ekhonni.backend.exception;

import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.util.JsonUtil;
import com.ekhonni.backend.util.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Author: Md Jahid Hasan
 * Date: 2/5/25
 */
@Component
@AllArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final JsonUtil jsonUtil;


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseEntity<ApiResponse<Void>> responseEntity = ResponseUtil.createResponse(HTTPStatus.FORBIDDEN);

        response.setStatus(responseEntity.getStatusCode().value());
        response.setContentType("application/json");

        String jsonResponse = jsonUtil.getObjectMapper().writeValueAsString(responseEntity.getBody());

        response.getWriter().write(jsonResponse);
    }
}

