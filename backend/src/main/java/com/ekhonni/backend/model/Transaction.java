/*
    Author: Asif Iqbal
 */

package com.ekhonni.backend.model;

import com.ekhonni.backend.abstractentity.AbstractEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction extends AbstractEntity {
    private double amount;
}
