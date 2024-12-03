/*
    Author: Asif Iqbal
 */

package com.ekhonni.backend.model;

import com.ekhonni.backend.abstractentity.abstractEntity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  // Annotation contains @Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Account extends abstractEntity {
    @OneToOne
    private User user;
    private double balance = 0.0;
    private String status;
}
