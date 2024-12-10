/**
 * Author: Rifat Shariar Sakil
 * Time: 2:25 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;


import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.projection.ProductProjection;
import com.ekhonni.backend.repository.ProductRepository;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public record ProductService(ProductRepository productRepository) {


       public void create(Product product){
           System.out.println(product);
           productRepository.save(product);
       }

//       public List<ProductProjection> getAll(){
//           return productRepository.findAllProjection();
//       }

    public Page<ProductProjection> getAll(Pageable pageable) {
        return productRepository.findAllProjectionPage(pageable);
    }


       public List<ProductProjection> getAllByCategoryId(Long categoryId){
           return productRepository.findAllByCategoryId(categoryId);
       }

       public Optional<ProductProjection> getOne(Long Id){
           return Optional.ofNullable(productRepository.findProductProjectionById(Id));
       }







//       public boolean delete(Long id){
//           Optional<Product> product = productRepository.findById(id);
//           if(product.isPresent()){
//               productRepository.deleteById(id);
//               return  true;
//           }
//           return false;
//
//       }




}
