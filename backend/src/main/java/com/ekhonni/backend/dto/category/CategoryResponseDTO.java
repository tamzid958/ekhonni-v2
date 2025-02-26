/**
 * Author: Rifat Shariar Sakil
 * Time: 5:42 PM
 * Date: 2/18/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class CategoryResponseDTO {
    String name;
    String imagePath;
}
