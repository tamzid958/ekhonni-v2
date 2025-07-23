package com.ekhonni.backend.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 * Author: Md Jahid Hasan
 * Date: 1/1/25
 */
@Component
public class RequestUtil {


    public String extractAuthorizationHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public String extractBearerToken(HttpServletRequest request) {
        String authHeader = extractAuthorizationHeader(request);

        return authHeader.substring(7);
    }


    public String extractAndNormalizeUri(HttpServletRequest request) {
        String uri = request.getRequestURI();

        return uri.replaceAll("(\\b[a-fA-F0-9\\-]{36}\\b)", "{id}")
                .replaceAll("/\\d+", "/{id}");
    }


}
