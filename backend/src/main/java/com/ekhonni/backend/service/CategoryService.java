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
import com.ekhonni.backend.exception.CategoryNotFoundException;
import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.projection.category.ViewerCategoryProjection;
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


    public void save(CategoryDTO categoryDTO) {
        Category parentCategory = null;
        if (categoryDTO.parentCategory() != null)
            parentCategory = categoryRepository.findByName(categoryDTO.parentCategory());
        if (categoryDTO.parentCategory() != null && parentCategory == null)
            throw new RuntimeException("parent category by this name not found");
        Category category = new Category(
                categoryDTO.name(),
                true,
                parentCategory
        );
        categoryRepository.save(category);
    }


    public CategorySubCategoryDTO getSub(String name) {
        Category parent = categoryRepository.findByName(name);
        if (parent == null) throw new CategoryNotFoundException();
        List<String> sequenceOfCategory = getSequence(name);
        CategorySubCategoryDTO categorySubCategoryDTO = new CategorySubCategoryDTO(parent.getName(), new ArrayList<>(), sequenceOfCategory);
        List<ViewerCategoryProjection> children = categoryRepository.findByParentCategoryAndActiveOrderByIdAsc(parent, true);
        for (ViewerCategoryProjection child : children) {
            categorySubCategoryDTO.getSubCategories().add(child.getName());
        }
        return categorySubCategoryDTO;
    }


    public List<CategorySubCategoryDTO> getAllCategorySubCategoryDTO() {

        List<CategorySubCategoryDTO> categorySubCategoryDTOS = new ArrayList<>();
        List<Category> rootCategories = categoryRepository.findByParentCategoryIsNullAndActive(true);
        for (Category rootCategory : rootCategories) {
            CategorySubCategoryDTO categorySubCategoryDTO = new CategorySubCategoryDTO(rootCategory.getName(), new ArrayList<>(), new ArrayList<>());
            List<ViewerCategoryProjection> subCategories = categoryRepository.findByParentCategoryAndActiveOrderByIdAsc(rootCategory, true);
            for (ViewerCategoryProjection subCategory : subCategories) {
                categorySubCategoryDTO.getSubCategories().add(subCategory.getName());
            }
            categorySubCategoryDTOS.add(categorySubCategoryDTO);
        }
        return categorySubCategoryDTOS;

    }


    public void delete(String name) {
        Category category = categoryRepository.findByName(name);
        if (category == null) {
            throw new CategoryNotFoundException();
        }
        categoryRepository.deleteCategoryById(category.getId());
    }


    @Transactional
    public void update(CategoryUpdateDTO categoryUpdateDTO) {
        Category category = categoryRepository.findByName(categoryUpdateDTO.name());
        if (category == null) {
            throw new CategoryNotFoundException();
        }
        category.setActive(categoryUpdateDTO.active());
        categoryRepository.save(category);

    }

    public List<String> getSequence(String name) {

        Category category = categoryRepository.findByNameAndActive(name, true);
        if (category == null) {
            throw new CategoryNotFoundException();
        }
        List<String> chain = new ArrayList<>();
        chain.add(category.getName());
        while (category.getParentCategory() != null) {
            chain.add(category.getParentCategory().getName());
            category = categoryRepository.findByNameAndActive(category.getParentCategory().getName(), true);
        }
        return chain;
    }


}
