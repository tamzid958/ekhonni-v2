/**
 * Author: Rifat Shariar Sakil
 * Time: 8:45 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;


import com.ekhonni.backend.dto.category.*;
import com.ekhonni.backend.exception.CategoryException;
import com.ekhonni.backend.imageupload.CategoryImageUploadEvent;
import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.projection.CategoryProjection;
import com.ekhonni.backend.projection.category.ViewerCategoryProjection;
import com.ekhonni.backend.repository.CategoryRepository;
import com.ekhonni.backend.repository.ProductRepository;
import com.ekhonni.backend.util.CloudinaryImageUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryService extends BaseService<Category, Long> {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq-custom.image-upload.exchange}")
    private String imageUploadExchange;

    @Value("${rabbitmq-custom.image-upload.category-routing-key}")
    private String categoryRoutingKey;


    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository,
                           RabbitTemplate rabbitTemplate
    ) {
        super(categoryRepository);
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.rabbitTemplate = rabbitTemplate;
    }


    @Transactional
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


       // String imagePath = cloudinaryImageUploadUtil.uploadImage(dto.image());

        Category category = new Category(
                dto.name(),
                null,
                true,
                parentCategory
        );
        categoryRepository.save(category);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                handleImageByRabbitMQ(dto, category);
            }
        });

        return "Category created successfully";
    }

    private void handleImageByRabbitMQ(CategoryDTO dto, Category category) {
        MultipartFile file = null;

        if (dto instanceof CategoryCreateDTO createDTO) {
            file = createDTO.image();
        } else if (dto instanceof CategoryUpdateDTO updateDTO) {
            file = updateDTO.image();
        }

        if (file == null || file.isEmpty()) {
            return;
        }

        byte[] imageBytes;
        try {
            imageBytes = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image bytes", e);
        }

        String filename = file.getOriginalFilename();
        String contentType = file.getContentType();

        rabbitTemplate.convertAndSend(imageUploadExchange, categoryRoutingKey,
                new CategoryImageUploadEvent(category.getId(), imageBytes, filename, contentType));
    }




    public CategorySubCategoryDTOV2 getSubV2(String name) {
        Category parent = categoryRepository.findByNameAndActive(name, true);
        if (parent == null) {
            throw new CategoryException("Category by this name not found");
        }
        List<String> sequenceOfCategory = getSequence(name);

        CategoryResponseDTO parentDTO = new CategoryResponseDTO(parent.getName(),parent.getImagePath(),0L, false);
        CategorySubCategoryDTOV2 categorySubCategoryDTO = new CategorySubCategoryDTOV2(parentDTO, new ArrayList<>(), sequenceOfCategory);



        List<CategoryProjection> children = categoryRepository.findProjectionByParentCategoryAndActiveOrderByIdAsc(parent, true);

        Map<Long, Long> productCounts = getProductCountsByCategory(children);
        Map<String, Boolean> subcategoryCheck = checkSubcategories(children);

        for (ViewerCategoryProjection child : children) {
            Long productCount = productCounts.getOrDefault(child.getId(), 0L);
            Boolean hasSubcategories = subcategoryCheck.getOrDefault(child.getName(), false);

            CategoryResponseDTO childDTO = new CategoryResponseDTO(
                    child.getName(),
                    child.getImagePath(),
                    productCount,
                    hasSubcategories
            );
            categorySubCategoryDTO.getSubCategories().add(childDTO);
        }
        return categorySubCategoryDTO;
    }


//    public List<CategorySubCategoryDTOV2> getAllCategorySubCategoryDTO() {
//
//        List<CategorySubCategoryDTOV2> categorySubCategoryDTOS = new ArrayList<>();
//        List<CategoryProjection> rootCategories = categoryRepository.findProjectionByParentCategoryIsNullAndActive(true);
//        for (CategoryProjection rootCategory : rootCategories) {
//            CategoryDTO rootDTO= new CategoryDTO(rootCategory.getName(),rootCategory.getImagePath());
//            CategorySubCategoryDTOV2 categorySubCategoryDTOV2 = new CategorySubCategoryDTOV2(rootDTO, new ArrayList<>(), new ArrayList<>());
//            List<ViewerCategoryProjection> subCategories = categoryRepository.findByParentCategoryNameAndActiveOrderByIdAsc(rootCategory.getName(), true);
//            for (ViewerCategoryProjection subCategory : subCategories) {
//                CategoryDTO childDTO = new CategoryDTO(subCategory.getName(),subCategory.getImagePath());
//                categorySubCategoryDTOV2.getSubCategories().add(childDTO);
//            }
//            categorySubCategoryDTOS.add(categorySubCategoryDTOV2);
//        }
//        return categorySubCategoryDTOS;
//
//    }




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

        // show error for products that are under this category
        if(productRepository.existsByCategoryName(name)){
            throw new CategoryException("Cannot delete category because products exists under this category");
        }

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
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    handleImageByRabbitMQ(categoryUpdateDTO, category);
                }
            });
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

    public List<CategorySubCategoryDTOV2> getTopCategories() {
        List<CategorySubCategoryDTOV2> dtos = new ArrayList<>();

//        List<CategoryResponseDTO> topCategories = Arrays.asList(
//                new CategoryResponseDTO("Travel & Nature", "http://res.cloudinary.com/dnetpmsx6/image/upload/default.jpg"),
//                new CategoryResponseDTO("Antiques", "http://res.cloudinary.com/dnetpmsx6/image/upload/default.jpg"),
//                new CategoryResponseDTO("Health & Beauty", "http://res.cloudinary.com/dnetpmsx6/image/upload/default.jpg"),
//                new CategoryResponseDTO("Sports & Outdoors", "http://res.cloudinary.com/dnetpmsx6/image/upload/default.jpg"),
//                new CategoryResponseDTO("Toys & Games", "http://res.cloudinary.com/dnetpmsx6/image/upload/default.jpg"),
//                new CategoryResponseDTO("Automotive", "http://res.cloudinary.com/dnetpmsx6/image/upload/default.jpg"),
//                new CategoryResponseDTO("Books & Stationery", "http://res.cloudinary.com/dnetpmsx6/image/upload/default.jpg"),
//                new CategoryResponseDTO("Groceries", "http://res.cloudinary.com/dnetpmsx6/image/upload/default.jpg"),
//                new CategoryResponseDTO("Office Supplies", "http://res.cloudinary.com/dnetpmsx6/image/upload/default.jpg")
//        );
//
//        for (CategoryResponseDTO category : topCategories) {
//            dtos.add(new CategorySubCategoryDTOV2(category, new ArrayList<>(), new ArrayList<>()));
//        }

        return dtos;
    }

    public CategorySubCategoryDTOV2 getAllCategorySubCategoryDTOV2() {

        CategoryResponseDTO rootCategoryResponseDTO = new CategoryResponseDTO("root", "null", 0L, false);
        CategorySubCategoryDTOV2 responseDTO = new CategorySubCategoryDTOV2();
        responseDTO.setCategory(rootCategoryResponseDTO);

        List<CategoryProjection> mainCategories = categoryRepository.findProjectionByParentCategoryIsNullAndActive(true);
        Map<Long, Long> productCounts = getProductCountsByCategory(mainCategories);
        Map<String, Boolean> subcategoryCheck = checkSubcategories(mainCategories);



        for (CategoryProjection mainCategory : mainCategories) {
            Long productCount = productCounts.getOrDefault(mainCategory.getId(), 0L);
            Boolean hasSubcategories = subcategoryCheck.getOrDefault(mainCategory.getName(), false);

            CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(
                    mainCategory.getName(),
                    mainCategory.getImagePath(),
                    productCount,
                    hasSubcategories
            );
            responseDTO.getSubCategories().add(categoryResponseDTO);
        }
        return  responseDTO;

    }

    public CategorySubCategoryDTO getSub(String name) {
        Category parent = categoryRepository.findByNameAndActive(name, true);
        if (parent == null) throw new CategoryException("Category by this name not found");
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


    public List<CategorySubCategoryDTO> getTopCategoriesV2() {
        List<CategoryProjection> mainCategories = categoryRepository.findProjectionByParentCategoryIsNullAndActive(true);
        Map<Long,String>categoryMapOfIdName = new HashMap<>();
        List<Long>ids = new ArrayList<>();
        for(CategoryProjection categoryProjection:mainCategories){
            ids.add(categoryProjection.getId());
            categoryMapOfIdName.put(categoryProjection.getId(),categoryProjection.getName());
        }
        List<Object[]> results = categoryRepository.countProductsByCategoriesAndDescendants(ids);


        results.sort((a, b) -> Long.compare((Long) b[1], (Long) a[1]));
        List<String>rootCategories = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            Object[] row = results.get(i);
            Long categoryId = (Long) row[0];
            rootCategories.add(categoryMapOfIdName.get(categoryId));
        }


        List<CategorySubCategoryDTO> categorySubCategoryDTOS = new ArrayList<>();
        for (String rootCategory : rootCategories) {
            CategorySubCategoryDTO categorySubCategoryDTO = new CategorySubCategoryDTO(rootCategory, new ArrayList<>(), new ArrayList<>());
            List<ViewerCategoryProjection> subCategories = categoryRepository.findByParentCategoryNameAndActiveOrderByIdAsc(rootCategory, true);
            for (ViewerCategoryProjection subCategory : subCategories) {
                categorySubCategoryDTO.getSubCategories().add(subCategory.getName());
            }
            categorySubCategoryDTOS.add(categorySubCategoryDTO);
        }
        return categorySubCategoryDTOS;

        //then based on count i want to sort the mainCategories

    }


    // get product count by categories
    public Map<Long, Long> getProductCountsByCategory(List<CategoryProjection>categoryProjections) {
        List<Long> categoryNames = new ArrayList<>();
        for (CategoryProjection categoryProjection : categoryProjections) {
            categoryNames.add(categoryProjection.getId());
        }
        List<Object[]> results = categoryRepository.countProductsByCategoriesAndDescendants(categoryNames);
        return results.stream()
                .collect(Collectors.toMap(
                        result -> (Long) result[0],
                        result -> (Long) result[1]
                ));
    }


    public Map<String, Boolean> checkSubcategories(List<CategoryProjection>categoryProjections) {
        List<String> categoryNames = new ArrayList<>();
        for (CategoryProjection categoryProjection : categoryProjections) {
            categoryNames.add(categoryProjection.getName());
        }

        List<Object[]> results = categoryRepository.hasSubcategories(categoryNames);

        return results.stream()
                .collect(Collectors.toMap(
                        result -> (String) result[0],
                        result -> (Boolean) result[1]
                ));
    }

}
