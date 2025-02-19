/**
 * Author: Rifat Shariar Sakil
 * Time: 2:25 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;


import com.ekhonni.backend.dto.product.*;
import com.ekhonni.backend.enums.BidStatus;
import com.ekhonni.backend.enums.ProductStatus;
import com.ekhonni.backend.exception.CategoryException;
import com.ekhonni.backend.exception.ProductNotFoundException;
import com.ekhonni.backend.exception.ProductNotUpdatedException;
import com.ekhonni.backend.filter.ProductFilter;
import com.ekhonni.backend.filter.UserProductFilter;
import com.ekhonni.backend.model.*;
import com.ekhonni.backend.projection.ProductProjection;
import com.ekhonni.backend.repository.*;
import com.ekhonni.backend.specification.SpecificationResult;
import com.ekhonni.backend.specificationbuilder.CommonProductSpecificationBuilder;
import com.ekhonni.backend.specificationbuilder.UserProductSpecificationBuilder;
import com.ekhonni.backend.util.AuthUtil;
import com.ekhonni.backend.util.CloudinaryImageUploadUtil;
import com.ekhonni.backend.util.PaginationUtil;
import com.ekhonni.backend.util.ProductProjectionConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService extends BaseService<Product, Long> {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final CloudinaryImageUploadUtil cloudinaryImageUploadUtil;
    private final ProductBoostRepository productBoostRepository;
    private final BidRepository bidRepository;



    public ProductService(ProductRepository productRepository, CategoryService categoryService,
                          CategoryRepository categoryRepository,
                          CloudinaryImageUploadUtil cloudinaryImageUploadUtil, ProductBoostRepository productBoostRepository,
                          BidRepository bidRepository
    ) {
        super(productRepository);
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.cloudinaryImageUploadUtil = cloudinaryImageUploadUtil;
        this.productBoostRepository = productBoostRepository;
        this.bidRepository = bidRepository;
    }


    @Transactional
    public void create(ProductCreateDTO dto) {
        try{
            User seller = AuthUtil.getAuthenticatedUser();

            Category category = Optional.ofNullable(categoryRepository.findByNameAndActive(dto.category(), true))
                    .orElseThrow(() -> new CategoryException("Category by this name not found"));

            List<ProductImage> images = cloudinaryImageUploadUtil.uploadImages(dto.images())
                    .stream()
                    .map(ProductImage::new)
                    .collect(Collectors.toList());

            Product product = new Product(
                    dto.title(), dto.subTitle(), dto.description(), dto.price(),
                    dto.division(), dto.address(), ProductStatus.PENDING_APPROVAL,
                    dto.condition(), dto.conditionDetails(), category, seller, images
            );

            productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



    public ProductResponseDTO getOne(Long id) {
        ProductProjection projection = productRepository.findProjectionById(id);
        if (projection == null) {
            throw new ProductNotFoundException("Product doesn't exist");
        }

        User user = getAuthenticatedUserSafely();
        if (!isUserAuthorizedToViewProduct(user, id)) {
            throw new ProductNotFoundException("Unauthorized to view this product");
        }

        return convertToProductResponseDTO(projection);
    }


    private User getAuthenticatedUserSafely() {
        try {
            return AuthUtil.getAuthenticatedUser();
        } catch (Exception ignored) {
            return null;
        }
    }


    private boolean isUserAuthorizedToViewProduct(User user, Long productId) {
        ProductProjection projection = productRepository.findProjectionById(productId);
        if (user == null) {
            return projection.getStatus() == ProductStatus.APPROVED;
        }

        User buyer = getBuyerByProductId(productId);
        boolean isSeller = user.getId().equals(projection.getSellerDTO().getId());
        boolean isBuyer = buyer!=null && user.getId().equals(buyer.getId());
        boolean isApproved = projection.getStatus() == ProductStatus.APPROVED;

        return isSeller || isBuyer || isApproved;
    }



    @Modifying
    @Transactional
    public String updateOne(Long id, ProductUpdateDTO dto) {

        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found for update"));

            Category category = categoryRepository.findByName(dto.category());
            if (category == null) throw new CategoryException("category by this name not found");


            List<String> imagePaths = cloudinaryImageUploadUtil.uploadImages(dto.images());
            List<ProductImage> images = new ArrayList<>();
            for (String imagePath : imagePaths) {
                ProductImage image = new ProductImage(imagePath);
                images.add(image);
            }

            product.getImages().clear();
            product.getImages().addAll(images);

            product.setTitle(dto.title());
            product.setSubTitle(dto.subTitle());
            product.setDescription(dto.description());
            product.setPrice(dto.price());
            product.setDivision(dto.division());
            product.setAddress(dto.address());
            product.setCondition(dto.condition());
            product.setConditionDetails(dto.conditionDetails());
            product.setCategory(category);


            return "updated";
        } catch (Exception e) {
            throw new ProductNotUpdatedException(e.getMessage());
        }

    }


    public UUID getSellerId(Long id) {
        return productRepository.findSellerIdById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }


    public Page<ProductResponseDTO> getAllFiltered(ProductFilter filter) {
        List<Long> categoryIds = extractCategoryIds(filter.getCategoryName());
        SpecificationResult specificationResult = CommonProductSpecificationBuilder.build(filter, categoryIds);
        Specification<Product> spec = specificationResult.getSpec();
        Pageable pageable = PaginationUtil.createPageable(filter.getPage() - 1, filter.getSize(), filter.getSortBy());

        return getProductsResponsePage(spec, pageable);
    }

    public Page<ProductResponseDTO> getAllFilteredForUser(UserProductFilter filter) {
        List<Long> categoryIds = extractCategoryIds(filter.getCategoryName());
        SpecificationResult specificationResult = UserProductSpecificationBuilder.build(filter, categoryIds);
        Specification<Product> spec = specificationResult.getSpec();
        Pageable pageable = PaginationUtil.createPageable(filter.getPage() - 1, filter.getSize(), filter.getSortBy());

        return getProductsResponsePage(spec, pageable);
    }


    /**
     * Extracts category IDs based on the provided category name.
     */
    public List<Long> extractCategoryIds(String categoryName) {
        if (categoryName != null && !categoryName.isEmpty()) {
            return categoryService.getRelatedActiveIds(categoryName);
        }
        return new ArrayList<>();
    }

    /**
     * Executes the common query logic: fetches paged product IDs based on a specification,
     * retrieves the corresponding projections, sorts them in the order of the given IDs,
     * and maps them to the final DTOs.
     */
    public Page<ProductResponseDTO> getProductsResponsePage(Specification<Product> spec, Pageable pageable) {
        // Fetch paged product IDs based on the specification
        Page<Long> page = productRepository.findAllFiltered(spec, pageable);
        List<Long> productIds = page.getContent();

        // Retrieve product projections for the fetched IDs
        List<ProductProjection> projections = productRepository.findByIdIn(productIds);

        // Sort projections to match the order of productIds
        Map<Long, Integer> idOrderMap = new HashMap<>();
        for (int i = 0; i < productIds.size(); i++) {
            idOrderMap.put(productIds.get(i), i);
        }
        projections.sort(Comparator.comparing(p -> idOrderMap.getOrDefault(p.getId(), Integer.MAX_VALUE)));

        // Convert each projection to a response DTO
        List<ProductResponseDTO> products = projections.stream()
                .map(this::convertToProductResponseDTO)
                .collect(Collectors.toList());

        // Return the final paged result
        return new PageImpl<>(products, pageable, page.getTotalElements());
    }



    private ProductSellerAndBuyerDTO getBuyerInfo(Long id){
        User buyer = getBuyerByProductId(id);
        if(buyer != null){
            return new ProductSellerAndBuyerDTO(buyer.getId(),buyer.getName());
        }
        return null;
    }
    private ProductResponseDTO convertToProductResponseDTO(ProductProjection projection) {
        ProductResponseDTO dto = ProductProjectionConverter.convert(projection);
        productBoostRepository.findByProductId(projection.getId()).ifPresent(boost ->
                dto.setBoostData(new ProductBoostResponseDTO(
                        boost.getBoostType(),
                        boost.getBoostedAt(),
                        boost.getExpiresAt()))
        );
        return dto;
    }


    public User getBuyerByProductId(Long productId) {
        Bid bid = bidRepository.findFirstByProductIdAndStatusInAndDeletedAtIsNull(
                        productId, Arrays.asList(BidStatus.ACCEPTED, BidStatus.PAID))
                .orElse(null);
        if (bid == null) return null;
        return bid.getBidder();
    }

}
