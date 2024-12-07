/**
 * Author: Rifat Shariar Sakil
 * Time: 2:46 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.model;

import com.ekhonni.backend.base.BaseEntity;
import com.ekhonni.backend.enums.ProductCondition;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Product")
public class Product extends BaseEntity {



    private String name;
    private Long price;
    private String description;
    private boolean approved = false;
    private boolean sold = false;

    @Enumerated(EnumType.STRING)
    private ProductCondition condition;




}
