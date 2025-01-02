/**
 * Author: Rifat Shariar Sakil
 * Time: 8:08 AM
 * Date: 1/2/2025
 * Project Name: backend
 */

package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Watchlist extends BaseEntity<Long> {

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User watcher;


    @ManyToMany
    @JoinTable(
            name = "watchlist_products",
            joinColumns = @JoinColumn(name = "watchlist_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

}
