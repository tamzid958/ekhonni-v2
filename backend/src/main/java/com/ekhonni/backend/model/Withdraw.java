package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Asif
 * Date: 12/7/2024
 * Time: 9:56 PM
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Withdraw extends BaseEntity<Long> {
    @NotNull
    @ManyToOne
    private Account account;

}
