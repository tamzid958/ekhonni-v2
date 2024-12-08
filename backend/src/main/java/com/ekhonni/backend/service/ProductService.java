/**
 * Author: Rifat Shariar Sakil
 * Time: 2:25 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;


import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.repository.ProductRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record ProductService(ProductRepository productRepository) {


       public Product create(Product product){
           return  productRepository.save(product);
       }

       public List<Product> getAll(){
           return  productRepository.findAll();
       }



       public List<Product> getAllByCategoryId(Long categoryId){
           System.out.println(productRepository.findAll());
           //return productRepository.findAllProductsByCategoryAndSubCategories(categoryId);
           return productRepository.findAllApprovedProductsInCategoryTree(categoryId);
       }

//       public List<ProductDTO> getAll(){
//           List<Product> products = productRepository.findAll();
//           return products.stream()
//                   .map(ProductDTO::new)
//                   .toList();
//
//           //return productRepository.findAll();
//       }


//       public List<Product> getAllByCategory(Long categoryId){
//           return productRepository.findAllProductByCategoryId(categoryId);
//       }
//       public ProductDTO getOne(Long id){
//
//           Product product = productRepository.findById(id)
//                   .orElseThrow(() -> new ResourceNotFoundException("product not found"));
//           return new ProductDTO(product);
//
//           //return productRepository.findById(id);
//       }



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
