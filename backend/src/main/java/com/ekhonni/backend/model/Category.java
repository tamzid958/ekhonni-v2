/**
 * Author: Rifat Shariar Sakil
 * Time: 8:46 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Category extends BaseEntity<Long> {

    @Column(nullable = false, unique = true)
    private String name;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

}