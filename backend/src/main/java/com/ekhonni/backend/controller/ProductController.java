/**
 * Author: Rifat Shariar Sakil
 * Time: 2:12 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.controller;

import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.service.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ekhonni-v2/products")
public record ProductController(ProductService productService){

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = productService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }


    @GetMapping("/{product_id}")
    public ResponseEntity<Optional<Product>> getOne(@PathVariable("product_id") Long productId) {
        Optional<Product> product = productService.getOne(productId);
        if (product.isPresent()) {
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @GetMapping("/by/{category_id}")
    public ResponseEntity<List<Product>> getAllByCategory(@PathVariable("category_id") Long categoryId) {
//        System.out.println(categoryId);
        List<Product> products = productService.getAllByCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }




    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(product));
    }


    @DeleteMapping("/{product_id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("product_id") Long id) {
        boolean isDeleted = productService.delete(id);
        if (isDeleted) return ResponseEntity.ok("Product deleted successfully");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");

    }


//    @PutMapping("/{id}")
//    public ResponseEntity<Product> updateProduct(@PathVariable Long id){
//        Product product = ProductService.update(id);
//        return ResponseEntity.status(HttpStatus.OK).body(product);
//    }



}
