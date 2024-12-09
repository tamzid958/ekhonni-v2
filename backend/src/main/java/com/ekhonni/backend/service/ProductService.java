/**
 * Author: Rifat Shariar Sakil
 * Time: 2:25 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;


import com.ekhonni.backend.dto.CategoryDTO;
import com.ekhonni.backend.dto.ProductDTO;
import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.projection.ProductProjection;
import com.ekhonni.backend.repository.ProductRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public record ProductService(ProductRepository productRepository) {


       public Product create(Product product){
           return  productRepository.save(product);
       }

       public List<ProductProjection> getAll(){
           List<ProductProjection> productProjections =   productRepository.findAllProjected();
           return productProjections;
       }



       public List<ProductProjection> getAllByCategoryId(Long categoryId){

           List<ProductProjection> products =  productRepository.findAllByCategoryId(categoryId);
           return products;
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
