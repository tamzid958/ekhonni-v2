/**
 * Author: Rifat Shariar Sakil
 * Time: 12:24 AM
 * Date: 1/9/2025
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.projection.implementation;

import com.ekhonni.backend.projection.BidProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BidProjectionImpl implements BidProjection {
    private Long id;
    private Long productId;
    private UUID bidderId;
    private String bidderName;
    private Double amount;
    private String status;

    public BidProjectionImpl(Long id, Long productId) {
        this.id = id;
        this.productId = productId;
    }
}
