package com.ekhonni.backend.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Author: Asif Iqbal
 * Date: 1/16/25
 */

@Slf4j
public class DomainUtil {
    public static String extractDomain(HttpServletRequest request) {
        String fullUrl = request.getRequestURL().toString();
        try {
            URL url = new URL(fullUrl);
            String domain = url.getProtocol() + "://" + url.getHost();

            if (url.getHost().endsWith(".com")) {
                return domain;
            }
            return null;

        } catch (MalformedURLException e) {
            log.error("Error parsing URL: {}", fullUrl, e);
            return null;
        }
    }
}
