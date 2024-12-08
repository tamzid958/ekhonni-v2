/*
    Author: Asif Iqbal
 */

package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account extends BaseEntity<Long> {
    @NonNull
    @OneToOne
    private User user;

    @Column(nullable = false)
    private double balance = 0.0;

    @NotBlank
    private String status;

}
