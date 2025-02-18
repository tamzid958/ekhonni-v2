/**
 * Author: Rifat Shariar Sakil
 * Time: 8:45 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;


import com.ekhonni.backend.dto.category.*;
import com.ekhonni.backend.exception.CategoryException;
import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.projection.category.ViewerCategoryProjection;
import com.ekhonni.backend.repository.CategoryRepository;
import com.ekhonni.backend.repository.ProductRepository;
import com.ekhonni.backend.util.CloudinaryImageUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class CategoryService extends BaseService<Category, Long> {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CloudinaryImageUploadUtil cloudinaryImageUploadUtil;


    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository,
                           CloudinaryImageUploadUtil cloudinaryImageUploadUtil
    ) {
        super(categoryRepository);
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.cloudinaryImageUploadUtil = cloudinaryImageUploadUtil;
    }


    public String save(CategoryCreateDTO dto) {
        Category existingCategory = categoryRepository.findByName(dto.name());

        if (existingCategory != null) {
            throw new CategoryException("Category already exists");
        }

        Category parentCategory = null;
        if (dto.parentCategory() != null) {
            parentCategory = categoryRepository.findByName(dto.parentCategory());

            if (parentCategory == null) {
                throw new CategoryException("Parent category not found");
            }

            if (!parentCategory.isActive()) {
                throw new CategoryException("Parent category is inactive");
            }
        }


        String imagePath = cloudinaryImageUploadUtil.uploadImage(dto.image());

        Category newCategory = new Category(
                dto.name(),
                imagePath,
                true,
                parentCategory
        );
        categoryRepository.save(newCategory);

        return "Category created successfully";
    }


        public CategorySubCategoryDTO getSub(String name) {
        Category parent = categoryRepository.findByNameAndActive(name, true);
        if (parent == null) throw new CategoryException("Category by this name not found");
        List<String> sequenceOfCategory = getSequence(name);
        CategoryDTO parentDTO = new CategoryDTO(parent.getName(),parent.getImagePath());
        CategorySubCategoryDTO categorySubCategoryDTO = new CategorySubCategoryDTO(parentDTO, new ArrayList<>(), sequenceOfCategory);
        List<ViewerCategoryProjection> children = categoryRepository.findByParentCategoryAndActiveOrderByIdAsc(parent, true);
        for (ViewerCategoryProjection child : children) {
            CategoryDTO childDTO = new CategoryDTO(child.getName(),child.getImagePath());
            categorySubCategoryDTO.getSubCategories().add(childDTO);
        }
        return categorySubCategoryDTO;
    }


    public List<CategorySubCategoryDTO> getAllCategorySubCategoryDTO() {

        List<CategorySubCategoryDTO> categorySubCategoryDTOS = new ArrayList<>();
        List<Category> rootCategories = categoryRepository.findByParentCategoryIsNullAndActive(true);
        for (Category rootCategory : rootCategories) {
            CategoryDTO rootDTO= new CategoryDTO(rootCategory.getName(),rootCategory.getImagePath());
            CategorySubCategoryDTO categorySubCategoryDTO = new CategorySubCategoryDTO(rootDTO, new ArrayList<>(), new ArrayList<>());
            List<ViewerCategoryProjection> subCategories = categoryRepository.findByParentCategoryAndActiveOrderByIdAsc(rootCategory, true);
            for (ViewerCategoryProjection subCategory : subCategories) {
                CategoryDTO childDTO = new CategoryDTO(subCategory.getName(),subCategory.getImagePath());
                categorySubCategoryDTO.getSubCategories().add(childDTO);
            }
            categorySubCategoryDTOS.add(categorySubCategoryDTO);
        }
        return categorySubCategoryDTOS;

    }


    @Transactional
    public void delete(String name) {
        // Check if the category exists
        if (!categoryRepository.existsByName(name)) {
            throw new CategoryException("Category by this name does not exist");
        }

        // Check if the category has subcategories
        if (categoryRepository.existsByParentCategoryName(name)) {
            throw new CategoryException("Cannot delete category because subcategories exist");
        }

        // show error for products that are under this category in future

        // Delete the category
        categoryRepository.deleteCategoryByName(name);
    }



    @Transactional
    public void update(CategoryUpdateDTO categoryUpdateDTO, String oldName)  {
        Category category = categoryRepository.findByName(oldName);
        if (category == null) {
            throw new CategoryException("Category by this old name not found");
        }

        if(categoryUpdateDTO.name()!=null){
            boolean existingCategory = categoryRepository.existsByName(categoryUpdateDTO.name());
            if (existingCategory) {
                throw new CategoryException("Category by this new name already exists");
            }
            category.setName(categoryUpdateDTO.name());
        }

        if (categoryUpdateDTO.active() != null) {
            category.setActive(categoryUpdateDTO.active());
        }

        if (categoryUpdateDTO.image() != null) {
            String imagePath = cloudinaryImageUploadUtil.uploadImage(categoryUpdateDTO.image());
            category.setImagePath(imagePath);
        }
    }


    public List<String> getSequence(String name) {

        Category category = categoryRepository.findByNameAndActive(name, true);
        if (category == null) {
            throw new CategoryException("Category by this name not found");
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
        if (category == null) throw new CategoryException("Category by this name not found");
        Long categoryId = category.getId();
        return categoryRepository.findRelatedActiveIds(categoryId);
    }


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


        Category currentCategory = category;
        while (currentCategory.getParentCategory() != null) {
            currentCategory = currentCategory.getParentCategory();
        }
        return currentCategory.getName();
    }


    public List<CategoryTreeDTO> getCategoryTree() {
        List<Category> categories = categoryRepository.findAll();
        return buildCategoryTree(categories);
    }


    public List<CategoryTreeDTO> getUserCategoryTree(UUID id) {

        List<Category> userCategories = productRepository.findCategoriesBySeller(id);
        Set<Category> allCategories = new HashSet<>();
        for (Category category : userCategories) {
            allCategories.add(category);
            fetchParentCategories(category, allCategories);
        }
        return buildCategoryTree(new ArrayList<>(allCategories));
    }


    private void fetchParentCategories(Category category, Set<Category> allCategories) {
        if (category.getParentCategory() != null) {
            allCategories.add(category.getParentCategory());
            fetchParentCategories(category.getParentCategory(), allCategories);
        }
    }


    private List<CategoryTreeDTO> buildCategoryTree(List<Category> categories) {

        Map<Long, CategoryTreeDTO> categoryMap = new HashMap<>();
        for (Category category : categories) {
            CategoryTreeDTO dto = new CategoryTreeDTO(
                    category.getId(),
                    category.getName(),
                    category.isActive()
            );
            categoryMap.put(category.getId(), dto);
        }


        List<CategoryTreeDTO> rootCategories = new ArrayList<>();
        for (Category category : categories) {
            if (category.getParentCategory() == null) {
                rootCategories.add(categoryMap.get(category.getId()));
            } else {
                CategoryTreeDTO parentDTO = categoryMap.get(category.getParentCategory().getId());
                if (parentDTO != null) {
                    parentDTO.addChild(categoryMap.get(category.getId()));
                }
            }
        }
        return rootCategories;
    }

    public List<CategorySubCategoryDTO> getTopCategories() {
        List<CategorySubCategoryDTO> dtos = new ArrayList<>();

        List<CategoryDTO> topCategories = Arrays.asList(
                new CategoryDTO("Travel & Nature", "http://res.cloudinary.com/dnetpmsx6/image/upload/default.jpg"),
                new CategoryDTO("Antiques", "http://res.cloudinary.com/dnetpmsx6/image/upload/default.jpg"),
                new CategoryDTO("Health & Beauty", "http://res.cloudinary.com/dnetpmsx6/image/upload/default.jpg"),
                new CategoryDTO("Sports & Outdoors", "http://res.cloudinary.com/dnetpmsx6/image/upload/default.jpg"),
                new CategoryDTO("Toys & Games", "http://res.cloudinary.com/dnetpmsx6/image/upload/default.jpg"),
                new CategoryDTO("Automotive", "http://res.cloudinary.com/dnetpmsx6/image/upload/default.jpg"),
                new CategoryDTO("Books & Stationery", "http://res.cloudinary.com/dnetpmsx6/image/upload/default.jpg"),
                new CategoryDTO("Groceries", "http://res.cloudinary.com/dnetpmsx6/image/upload/default.jpg"),
                new CategoryDTO("Office Supplies", "http://res.cloudinary.com/dnetpmsx6/image/upload/default.jpg")
        );

        for (CategoryDTO category : topCategories) {
            dtos.add(new CategorySubCategoryDTO(category, new ArrayList<>(), new ArrayList<>()));
        }

        return dtos;
    }
}
