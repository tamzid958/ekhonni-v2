package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Md Jahid Hasan
 * Date: 12/29/24
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Privilege extends BaseEntity<Long> {
    private String name;
    private String description;
    private String httpMethod;
    private String endpoint;
}
