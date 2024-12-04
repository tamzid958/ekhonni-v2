/**
 * Author: Rifat Shariar Sakil
 * Time: 8:45 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;

import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record CategoryService(CategoryRepository categoryRepository){
   public List<Category> getAll(){
       return categoryRepository.findAll();
   }

   public Category create(Category category){
       return categoryRepository.save(category);
   }
}
