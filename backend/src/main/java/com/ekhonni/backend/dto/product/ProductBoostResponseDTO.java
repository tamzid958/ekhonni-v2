package com.ekhonni.backend.dto.product;

import com.ekhonni.backend.enums.BoostType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductBoostResponseDTO {
    private BoostType boostType;
    private LocalDateTime boostedAt;
    private LocalDateTime expiresAt;
}