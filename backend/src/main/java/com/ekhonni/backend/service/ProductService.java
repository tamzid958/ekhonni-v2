/**
 * Author: Rifat Shariar Sakil
 * Time: 2:25 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;


import com.ekhonni.backend.Request.ProductRequest;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.projection.ProductProjection;
import com.ekhonni.backend.repository.ProductRepository;
import com.ekhonni.backend.specification.ProductSpecifications;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

       private final ProductRepository productRepository;



       public ProductService(ProductRepository productRepository, ProductSpecifications productSpecifications) {
         this.productRepository = productRepository;
       }

       public void create(Product product){
           productRepository.save(product);
       }

//       public Page<ProductProjection> getAll(Pageable pageable) {
//           return productRepository.findAllProjection(pageable);
//       }
public List<ProductProjection> getAll(ProductRequest productRequest) {


    Specification<Product> spec = Specification.where(null);


        spec = spec.and(ProductSpecifications.hasMinimumPrice(productRequest.getMinPrice()));

        List<ProductProjection> result = productRepository.findBy(spec, q -> q
//                .project("name")
                .as(ProductProjection .class)
                .all()
        );

    return result;
}


       public Page<ProductProjection> getAllByCategoryId(Long categoryId, Pageable pageable){
           return productRepository.findAllProjectionByCategoryId(categoryId, pageable);
       }


       public Optional<ProductProjection> getOne(Long Id){
           return Optional.ofNullable(productRepository.findProductProjectionById(Id));
       }

       public Page<ProductProjection> search(String searchText, Pageable pageable){
           return productRepository.searchProducts(searchText, pageable);
       }


       public boolean delete(Long id){
           if (productRepository.existsById(id)) {
               productRepository.deleteById(id);
               return true;
           }
           return false;
       }

       @Transactional
       public boolean approveProduct(Long id){
           productRepository.findById(id).ifPresent(product -> {
               product.setApproved(true);
               productRepository.save(product);
           });
           return productRepository.existsById(id);
       }

       public boolean declineProduct(Long id){
           productRepository.findById(id).ifPresent(product -> {
               // notify seller
           });
           return true;
       }




}
