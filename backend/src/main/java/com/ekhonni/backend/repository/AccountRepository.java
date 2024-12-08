package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends BaseRepository<Account, Long> {
}
