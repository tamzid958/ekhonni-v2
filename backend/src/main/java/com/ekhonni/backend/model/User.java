package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import com.ekhonni.backend.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity<UUID> implements UserDetails {

    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    @Enumerated(EnumType.STRING)
    private Role role;
    @NotBlank
    private String phone;
    @NotBlank
    private String address;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getRole().toString()));
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }
}

