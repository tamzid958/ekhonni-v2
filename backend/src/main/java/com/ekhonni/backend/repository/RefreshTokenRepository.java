package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.RefreshToken;
import org.springframework.stereotype.Repository;

/**
 * Author: Md Jahid Hasan
 * Date: 1/6/25
 */
@Repository
public interface RefreshTokenRepository extends BaseRepository<RefreshToken, Long> {
    RefreshToken getByValue(String token);
}
