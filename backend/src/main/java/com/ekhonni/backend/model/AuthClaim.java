package com.ekhonni.backend.model;

import lombok.*;

import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 * Date: 1/13/25
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthClaim {
    private UUID id;
    private String role;
    private AuthToken authToken;
}
