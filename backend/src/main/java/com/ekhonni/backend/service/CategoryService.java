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
import java.util.Optional;

@Service
public record CategoryService(CategoryRepository categoryRepository){
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

//    public List<Category> getAll() {
//        return categoryRepository.findTopLevelCategories();
//    }


    public Optional<Category> getById(Long id) {
        return categoryRepository.findById(id);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
