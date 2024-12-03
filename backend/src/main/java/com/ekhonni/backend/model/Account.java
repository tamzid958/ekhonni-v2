/*
    Author: Asif Iqbal
 */

package com.ekhonni.backend.model;

import com.ekhonni.backend.abstractentity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account extends AbstractEntity {
    @OneToOne
    private User user;

    @Column(nullable = false)
    @Value("${account.balance:0.0}")
    private double balance;

    @Column(nullable = false)
    private String status;

}
