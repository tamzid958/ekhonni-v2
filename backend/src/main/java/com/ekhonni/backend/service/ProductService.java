/**
 * Author: Rifat Shariar Sakil
 * Time: 2:25 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;

import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record ProductService(ProductRepository productRepository) {


       public Product create(Product product){
           return  productRepository.save(product);
       }

       public List<Product> getAll(){
           return productRepository.findAll();
       }
       public List<Product> getAllByCategory(Long categoryId){
           return productRepository.findAllProductByCategoryId(categoryId);
       }
       public Optional<Product> getOne(Long id){
           return productRepository.findById(id);
       }



       public boolean delete(Long id){
           Optional<Product> product = productRepository.findById(id);
           if(product.isPresent()){
               productRepository.deleteById(id);
               return  true;
           }
           return false;

       }




}
