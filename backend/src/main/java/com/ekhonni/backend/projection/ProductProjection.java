/**
 * Author: Rifat Shariar Sakil
 * Time: 8:18 PM
 * Date: 12/9/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.projection;

import com.ekhonni.backend.enums.ProductCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class ProductProjection {
    private Long id;
    private Double price;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ProductCondition condition;
    private Long categoryId;
    private String categoryName;
    private List<String> imagePaths;
}
