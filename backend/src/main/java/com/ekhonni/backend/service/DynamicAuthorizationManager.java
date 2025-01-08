package com.ekhonni.backend.service;

import com.ekhonni.backend.exception.RoleNotFoundException;
import com.ekhonni.backend.model.Privilege;
import com.ekhonni.backend.model.Role;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.util.RequestUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * Author: Md Jahid Hasan
 * Date: 12/31/24
 */
@Component
@AllArgsConstructor
public class DynamicAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final PrivilegeService privilegeService;
    private final RoleService roleService;
    private final RequestUtil requestUtil;

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        AuthorizationManager.super.verify(authentication, context);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        Authentication authenticatedUser = authentication.get();
        Role role = null;

        if (!(authenticatedUser instanceof AnonymousAuthenticationToken)) {
            User user = (User) authenticatedUser.getPrincipal();
            try {
                role = user.getRole();
            } catch (RoleNotFoundException e) {
                throw new RuntimeException("Role not found");
            }
        }

        String httpMethod = context.getRequest().getMethod();

        String endpoint = requestUtil.extractAndNormalizeUri(context.getRequest());


        Privilege privilege = privilegeService.getByHttpMethodAndEndpoint(httpMethod, endpoint);

        boolean granted = roleService.hasPrivilegeAccess(role, privilege);

        return new AuthorizationDecision(granted);

    }
}
