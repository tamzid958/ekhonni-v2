/**
 * Author: Rifat Shariar Sakil
 * Time: 8:45 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;


import com.ekhonni.backend.dto.CategoryDTO;
import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public record CategoryService(CategoryRepository categoryRepository){


    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public List<CategoryDTO> getAll(){

        List<Category> categories = categoryRepository.findTopLevelCategories();
        System.out.println(categories);


        if (categories.isEmpty()) {
            throw new RuntimeException("No categories found!");
        }

        return categories.stream()
                .map(CategoryDTO::new)
                .collect(Collectors.toList());
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
