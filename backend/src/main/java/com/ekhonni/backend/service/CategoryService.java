/**
 * Author: Rifat Shariar Sakil
 * Time: 8:45 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;


import com.ekhonni.backend.dto.CategoryDTO;
import com.ekhonni.backend.dto.CategorySubCategoryDTO;
import com.ekhonni.backend.dto.CategoryUpdateDTO;
import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.projection.CategoryProjection;
import com.ekhonni.backend.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CategoryService extends BaseService<Category, Long> {

    CategoryRepository categoryRepository;


    public CategoryService(CategoryRepository categoryRepository) {
        super(categoryRepository);
        this.categoryRepository = categoryRepository;
    }

    public Category getByName(String name) {
        return categoryRepository.findByNameAndActive(name, true);
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
    public CategorySubCategoryDTO getSubCategories(String name) {
        Category category = getByName(name);
        if (category == null) throw new RuntimeException("no category found by this name");

        CategorySubCategoryDTO categorySubCategoryDTO = new CategorySubCategoryDTO(category.getName(), new ArrayList<>());
        List<CategoryProjection> categoryProjections = categoryRepository.findByParentCategoryAndActiveOrderByIdAsc(category, true);
        for (CategoryProjection categoryProjection : categoryProjections) {
            categorySubCategoryDTO.getSubCategories().add(categoryProjection.getName());
        }
        return categorySubCategoryDTO;
    }


    public List<CategorySubCategoryDTO> getAllCategories() {
        List<Category> rootCategories = categoryRepository.findByParentCategoryIsNullAndActive(true);
        List<CategorySubCategoryDTO> categorySubCategoryDTOS = new ArrayList<>();

        for (Category rootCategory : rootCategories) {
            CategorySubCategoryDTO categorySubCategoryDTO = new CategorySubCategoryDTO(rootCategory.getName(), new ArrayList<>());
            List<CategoryProjection> subCategories = categoryRepository.findByParentCategoryAndActiveOrderByIdAsc(rootCategory, true);
            for (CategoryProjection categoryProjection : subCategories) {
                categorySubCategoryDTO.getSubCategories().add(categoryProjection.getName());
            }
            categorySubCategoryDTOS.add(categorySubCategoryDTO);
        }
        return categorySubCategoryDTOS;

    }


    public void delete(String name) {
        Category category = getByName(name);
        if (category == null) {
            throw new RuntimeException("category not found");
        }
        categoryRepository.deleteCategoryById(category.getId());
    }


    @Transactional
    public void updateCategory(CategoryUpdateDTO categoryUpdateDTO) {
        Category category = categoryRepository.findByName(categoryUpdateDTO.name());
        if (category == null) {
            throw new RuntimeException("no such category found by this name");
        }
        System.out.println(categoryUpdateDTO);
        category.setActive(categoryUpdateDTO.active());
        System.out.println(category);
        categoryRepository.save(category);

    }


}
