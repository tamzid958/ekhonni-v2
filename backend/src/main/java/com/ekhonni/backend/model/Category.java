/**
 * Author: Rifat Shariar Sakil
 * Time: 8:46 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.model;

import com.ekhonni.backend.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JoinColumn(name = "cat_parent_id")
    @JsonBackReference
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Category> childrenCategory;
}