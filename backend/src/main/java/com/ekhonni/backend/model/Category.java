/**
 * Author: Rifat Shariar Sakil
 * Time: 8:46 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.model;

import com.ekhonni.backend.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Category extends BaseEntity {
    private String categoryName;

    @JsonProperty("isActive")
    private boolean isActive;


    @Column(name = "categoryParentId")
    private Long categoryParentId;
}
