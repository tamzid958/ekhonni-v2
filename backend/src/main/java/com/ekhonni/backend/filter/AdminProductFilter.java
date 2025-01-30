/**
 * Author: Rifat Shariar Sakil
 * Time: 2:06 PM
 * Date: 1/30/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.filter;

import com.ekhonni.backend.enums.ProductStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdminProductFilter extends ProductFilter{
    private ProductStatus productStatus;
}
