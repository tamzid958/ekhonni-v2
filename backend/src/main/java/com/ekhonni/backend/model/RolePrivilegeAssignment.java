package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Md Jahid Hasan
 * Date: 12/30/24
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RolePrivilegeAssignment extends BaseEntity<Long> {
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    private Long privilegeId;
}
