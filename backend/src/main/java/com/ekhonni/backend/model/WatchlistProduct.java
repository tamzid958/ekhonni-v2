/**
 * Author: Rifat Shariar Sakil
 * Time: 8:08 AM
 * Date: 1/2/2025
 * Project Name: backend
 */

package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(
        name = "watchlist_product",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "product_id"},
                        name = "uk_user_product"
                )
        }
)
@ToString
public class WatchlistProduct extends BaseEntity<Long> {

    @NotNull(message = "User must not be null")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "Product must not be null")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}

