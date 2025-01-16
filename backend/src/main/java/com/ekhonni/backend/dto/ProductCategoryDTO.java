/**
 * Author: Rifat Shariar Sakil
 * Time: 4:37 PM
 * Date: 1/9/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ProductCategoryDTO {
    private Long id;
    private String name;
}
