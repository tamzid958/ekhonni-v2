package com.ekhonni.backend.model;

import com.ekhonni.backend.abstractentity.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends AbstractEntity {
    @OneToOne(mappedBy = "user")
    private Account account;
}
