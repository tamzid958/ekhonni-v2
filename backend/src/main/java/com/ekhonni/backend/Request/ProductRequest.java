/**
 * Author: Rifat Shariar Sakil
 * Time: 12:09 PM
 * Date: 12/22/2024
 * Project Name: backend
 */

package com.ekhonni.backend.Request;

import com.ekhonni.backend.enums.ProductCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private ProductCondition condition;
    private Double minPrice;
    private Double maxPrice;
    private Boolean bidAmountLowToHigh;
}
