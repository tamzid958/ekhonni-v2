package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
//@SQLDelete(sql = "UPDATE user SET deleted=true WHERE id=?")
//@Where(clause = "deleted=false")
public class User extends BaseEntity<UUID> {

    private String name;
    private String email;
    private String password;
    private String role;
    private String phone;
    private String address;

    @OneToOne (mappedBy = "user")
    private Account account;

    public User(String name, String email, String password, String role, String phone, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.address = address;
    }
}

