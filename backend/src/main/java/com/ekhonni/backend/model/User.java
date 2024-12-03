package com.ekhonni.backend.model;

import com.ekhonni.backend.abstractentity.abstractEntity;
import jakarta.persistence.OneToOne;

public class User extends abstractEntity {
    @OneToOne(mappedBy = "user")
    private Account account;
}
