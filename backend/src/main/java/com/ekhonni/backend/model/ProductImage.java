/**
 * Author: Rifat Shariar Sakil
 * Time: 8:12 PM
 * Date: 1/9/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage  extends BaseEntity<Long> {
    @NotBlank
    private String imagePath;

}
