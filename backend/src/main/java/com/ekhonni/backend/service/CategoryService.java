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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CategoryService extends BaseService<Category, Long> {

    CategoryRepository categoryRepository;


    public CategoryService(CategoryRepository categoryRepository) {
        super(categoryRepository);
        this.categoryRepository = categoryRepository;
    }

    public Category getByName(String name) {
        return categoryRepository.findByName(name);
    }


    // done
    public void save(CategoryDTO categoryDTO) {
        Category parentCategory = null;
        if (categoryDTO.parentCategory() != null) parentCategory = getByName(categoryDTO.parentCategory());
        if (categoryDTO.parentCategory() != null && parentCategory == null)
            throw new RuntimeException("parent category by this name not found");
        Category category = new Category(
                categoryDTO.name(),
                true,
                parentCategory
        );
        categoryRepository.save(category);
    }

    //done
    public List<CategoryProjection> getSub(String name) {
        Category category = getByName(name);
        if (category == null) throw new RuntimeException("no category found by this name");
        return categoryRepository.findSub(category.getId());
    }


    public Map<Category, List<CategoryProjection>> getRootAndFirstSub() {
        List<Category> rootCategories = categoryRepository.findByParentCategoryIsNull();
        Map<Category, List<CategoryProjection>> parentChildCategories = new LinkedHashMap<>();

        for (Category rootCategory : rootCategories) {
            List<CategoryProjection> subCategories = categoryRepository.findByParentCategoryOrderByIdAsc(rootCategory);
            parentChildCategories.put(rootCategory, subCategories);
        }
        return parentChildCategories;
    }


    public void delete(Long id) {
        categoryRepository.deleteCategoryById(id);
    }

    public List<CategoryProjection> getFeatured() {
        return categoryRepository.findFeatured();
    }


}
