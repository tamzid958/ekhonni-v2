/**
 * Author: Rifat Shariar Sakil
 * Time: 5:46 PM
 * Date: 1/30/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.specification;



import com.ekhonni.backend.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

@Getter
@RequiredArgsConstructor
public class SpecificationResult {
    private final Specification<Product> spec;
    private final boolean hasConditions;
}
;