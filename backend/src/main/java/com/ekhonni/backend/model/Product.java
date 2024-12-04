/**
 * Author: Rifat Shariar Sakil
 * Time: 2:46 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.model;

import com.ekhonni.backend.base.BaseEntity;
import com.ekhonni.backend.enums.UsedState;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "Product")

// do not use data, why?
public class Product extends BaseEntity {


    // solve product attribute names
    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private Long productPrice;


    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_approved")
    private boolean productApproved = false;


    @Column(name = "product_sold")
    private boolean productSold = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "used_state")
    private UsedState usedState;




    @Column(name = "category_id")
    private Long categoryId;




//    @Column(name = "product_image_path")
//    private String productImagePath;


}
