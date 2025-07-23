/**
 * Author: Rifat Shariar Sakil
 * Time: 2:20 PM
 * Date: 2/12/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto.product;

import com.ekhonni.backend.enums.BoostType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductBoostDTO {
    private Long productId;
    private BoostType boostType;
}
