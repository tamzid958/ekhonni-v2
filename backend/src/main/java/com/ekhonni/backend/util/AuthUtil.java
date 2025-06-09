/**
 * Author: Rifat Shariar Sakil
 * Time: 11:56 AM
 * Date: 1/2/2025
 * Project Name: backend
 */

package com.ekhonni.backend.util;

import com.ekhonni.backend.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;


// need to change
public class AuthUtil {

    private AuthUtil() {

    }

    public static User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication instanceof AnonymousAuthenticationFilter) {
            throw new RuntimeException("User not authenticated");
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User)) {
            throw new RuntimeException("Invalid user details in authentication context");
        }
        return (User) principal;
    }
}
