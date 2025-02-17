/**
 * Author: Rifat Shariar Sakil
 * Time: 12:27 PM
 * Date: 2/4/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.util;

import com.ekhonni.backend.enums.ProductSort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationUtil {

    public static Pageable createPageable(int page, int size, ProductSort sortBy) {
        Sort sort = Sort.unsorted();

        if (sortBy != null) {
            switch (sortBy) {
                case priceLowToHigh:
                    sort = Sort.by(Sort.Direction.ASC, "price");
                    break;
                case priceHighToLow:
                    sort = Sort.by(Sort.Direction.DESC, "price");
                    break;
                case newlyListed:
                    sort = Sort.by(Sort.Direction.DESC, "createdAt");
                    break;
            }
        }


        page = Math.max(page, 0);
        size = Math.max(5, size);

        System.out.println(page + "   gg  " + size);
        
        return PageRequest.of(page, size, sort);
    }
}
