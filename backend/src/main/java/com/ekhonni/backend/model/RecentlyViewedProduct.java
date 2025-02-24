/**
 * Author: Rifat Shariar Sakil
 * Time: 2:23 PM
 * Date: 2/24/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(
        name = "recently_viewed_product",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "product_id"},
                        name = "uk_recently_viewed"
                )
        }
)
@ToString
public class RecentlyViewedProduct extends BaseEntity<Long> {

    @NotNull(message = "User must not be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "Product must not be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}

