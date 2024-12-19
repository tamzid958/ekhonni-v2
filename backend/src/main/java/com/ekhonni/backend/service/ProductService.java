/**
 * Author: Rifat Shariar Sakil
 * Time: 2:25 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;


import com.ekhonni.backend.dto.ProductDTO;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public record ProductService(ProductRepository productRepository) {


       public Product create(Product product){
           return  productRepository.save(product);
       }

       public List<ProductDTO> getAll(){
           // return  productRepository.findAll();
           List<Product> products =   productRepository.findAll();

           if (products.isEmpty()) {
               throw new RuntimeException("No products found!");
           }

           return products.stream()
                   .map(ProductDTO::new)
                   .collect(Collectors.toList());
       }



       public List<ProductDTO> getAllByCategoryId(Long categoryId){

           List<Product> products =  productRepository.findAllProductsInCategoryTree(categoryId);
           if (products.isEmpty()) {
               throw new RuntimeException("No products found!");
           }

           return products.stream()
                   .map(ProductDTO::new)
                   .collect(Collectors.toList());
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
