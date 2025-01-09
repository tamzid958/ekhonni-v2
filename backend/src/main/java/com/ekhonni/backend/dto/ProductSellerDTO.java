/**
 * Author: Rifat Shariar Sakil
 * Time: 3:55 PM
 * Date: 1/9/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class ProductSellerDTO {
    private UUID sellerId;
    private String sellerName;

}
