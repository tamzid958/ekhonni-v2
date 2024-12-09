/**
 * Author: Rifat Shariar Sakil
 * Time: 8:45 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;


import com.ekhonni.backend.dto.CategoryDTO;
import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.projection.CategoryProjection;
import com.ekhonni.backend.repository.CategoryRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public record CategoryService(CategoryRepository categoryRepository){


    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public List<CategoryProjection> getAll(){
     return categoryRepository.findAllProjected();
    }

    public CategoryDTO getOne(Long id) {
        Category category = categoryRepository.findById(id)
                .orElse(null);
        return category != null ? new CategoryDTO(category) : null;
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
//    public Optional<Category> getById(Long id) {
//        return categoryRepository.findById(id);
//    }
//
//    public void delete(Long id) {
//        categoryRepository.deleteById(id);
//    }


}
