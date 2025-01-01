/**
 * Author: Rifat Shariar Sakil
 * Time: 2:25 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;


import com.ekhonni.backend.dto.ProductDTO;
import com.ekhonni.backend.filter.ProductFilter;
import com.ekhonni.backend.projection.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    void create(ProductDTO productDTO);

    List<ProductProjection> getAllFiltered(ProductFilter productFilter);

    List<ProductProjection> search(String searchText, Pageable pageable);

    boolean approveProduct(Long id);

    boolean declineProduct(Long id);

    <P> Page<P> getAll(Class<P> projection, Pageable pageable);

    <P> P get(Long id, Class<P> projection);

    <D> D update(Long id, D dto);

    void softDelete(Long id);


}
