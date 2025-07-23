/**
 * Author: Rifat Shariar Sakil
 * Time: 3:31 PM
 * Date: 1/14/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.filter;

import com.ekhonni.backend.enums.ProductStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserProductFilter extends ProductFilter {
    private ProductStatus status;
}
