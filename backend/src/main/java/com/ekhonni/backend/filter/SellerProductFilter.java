/**
 * Author: Rifat Shariar Sakil
 * Time: 12:22 PM
 * Date: 2/9/2025
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.filter;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class SellerProductFilter extends ProductFilter {
    private UUID userId;
}
