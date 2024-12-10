/**
 * Author: Rifat Shariar Sakil
 * Time: 8:45 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;


import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.projection.CategoryProjection;
import com.ekhonni.backend.repository.CategoryRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record CategoryService(CategoryRepository categoryRepository){


    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public List<CategoryProjection> getAll(){
     return categoryRepository.findAllProjected();
    }

    public CategoryProjection getOne(Long id) {
       return categoryRepository.findCategoryProjectionById(id);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }


//    public List<CategoryDTO> getAll() {
//        List<Category> categories = categoryRepository.findAll();
//        return categories.stream()
//                .map(CategoryDTO::new)
//                .toList();
//    }
//
//    public CategoryDTO getById(Long id) {
//        Category category = categoryRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
//        return new CategoryDTO(category);
//    }
//

//
//    public Optional<Category> getById(Long id) {
//        return categoryRepository.findById(id);
//    }
//



}
