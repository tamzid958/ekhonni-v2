/**
 * Author: Rifat Shariar Sakil
 * Time: 3:35 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;


@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;


    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
