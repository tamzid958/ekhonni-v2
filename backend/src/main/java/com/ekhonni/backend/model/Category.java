/**
 * Author: Rifat Shariar Sakil
 * Time: 8:46 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.model;

import com.ekhonni.backend.base.BaseEntity;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Category extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private boolean active = true;


    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    @JsonIgnore
    private List<Category> subCategories;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Product> products;


}