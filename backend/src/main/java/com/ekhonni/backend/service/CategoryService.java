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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public record CategoryService(CategoryRepository categoryRepository){


    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public List<CategoryProjection> getSub(Long id) {
       return categoryRepository.findSub(id);
    }

    public void delete(Long id) {
        categoryRepository.deleteCategoryById(id);
    }

    public List<CategoryProjection> getFeatured(){
        return categoryRepository.findFeatured();
    }



}
