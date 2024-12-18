package com.ekhonni.backend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 * Author: Md Jahid Hasan
 * Date: 12/18/24
 */

@Component
@RequestScope
@Getter
@Setter
public class UserRequestScopedBean {
    private String jwt;
}
