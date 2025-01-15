/**
 * Author: Rifat Shariar Sakil
 * Time: 8:45 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;


import com.ekhonni.backend.dto.CategoryCreateDTO;
import com.ekhonni.backend.dto.CategorySubCategoryDTO;
import com.ekhonni.backend.dto.CategoryUpdateDTO;
import com.ekhonni.backend.exception.CategoryNotFoundException;
import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.projection.category.ViewerCategoryProjection;
import com.ekhonni.backend.repository.CategoryRepository;
import com.ekhonni.backend.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class CategoryService extends BaseService<Category, Long> {

    CategoryRepository categoryRepository;
    ProductRepository productRepository;
    private final Map<Long, String> rootCategoryCache = new HashMap<>();


    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        super(categoryRepository);
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }


    public String save(CategoryCreateDTO dto) {
        Category old = categoryRepository.findByName(dto.name());
        if (old != null) throw new CategoryNotFoundException("Category already exists");

        Category parentCategory = null;
        if (dto.parentCategory() != null)
            parentCategory = categoryRepository.findByName(dto.parentCategory());
        if (dto.parentCategory() != null && parentCategory == null)
            throw new CategoryNotFoundException("Parent category by this name not found");

        if (dto.parentCategory() != null && !parentCategory.isActive()) {
            throw new CategoryNotFoundException("Parent category is inactive");
        }

        Category category = new Category(
                dto.name(),
                true,
                parentCategory
        );
        categoryRepository.save(category);
        return "created";
    }


    public CategorySubCategoryDTO getSub(String name) {
        Category parent = categoryRepository.findByNameAndActive(name, true);
        if (parent == null) throw new CategoryNotFoundException("Category by this name not found");
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
            throw new CategoryNotFoundException("category by this name not found");
        }
        categoryRepository.deleteCategoryById(category.getId());
    }


    @Transactional
    public void update(CategoryUpdateDTO categoryUpdateDTO) {
        //category not found
        Category category = categoryRepository.findByName(categoryUpdateDTO.name());
        if (category == null) {
            throw new CategoryNotFoundException("Category by this name not found");
        }

        // parent is inactive
//        Category parent = category.getParentCategory();
//        if(parent!=null && !parent.isActive()){
//            throw new CategoryNotFoundException("Parent category is inactive");
//        }

        //update th
        category.setActive(categoryUpdateDTO.active());
        categoryRepository.save(category);

    }

    public List<String> getSequence(String name) {

        Category category = categoryRepository.findByNameAndActive(name, true);
        if (category == null) {
            throw new CategoryNotFoundException("Category by this name not found");
        }
        List<String> sequence = new ArrayList<>();
        sequence.add(category.getName());
        while (category.getParentCategory() != null) {
            sequence.add(category.getParentCategory().getName());
            category = categoryRepository.findByNameAndActive(category.getParentCategory().getName(), true);
        }
        return sequence;
    }


    public List<Long> getRelatedActiveIds(String name) {
        Category category = categoryRepository.findByNameAndActive(name, true);
        if (category == null) throw new CategoryNotFoundException("Category by this name not found");
        Long categoryId = category.getId();
        return categoryRepository.findRelatedActiveIds(categoryId);
    }


//    public List<CategorySubCategoryDTO> getUserCategory(UUID userId) {
//
//    }


    public Set<String> findRootCategoriesBySeller(UUID sellerId) {

        List<Category> categories = productRepository.findCategoriesBySeller(sellerId);
        Set<String> rootCategoryNames = new HashSet<>();


        for (Category category : categories) {
            String rootCategoryName = getRootCategoryName(category);
            rootCategoryNames.add(rootCategoryName);
        }
        return rootCategoryNames;
    }


    private String getRootCategoryName(Category category) {

        if (rootCategoryCache.containsKey(category.getId())) {
            return rootCategoryCache.get(category.getId());
        }

        Category currentCategory = category;
        while (currentCategory.getParentCategory() != null) {
            currentCategory = currentCategory.getParentCategory();
        }

        String rootCategoryName = currentCategory.getName();
        rootCategoryCache.put(category.getId(), rootCategoryName);

        return rootCategoryName;
    }

}
