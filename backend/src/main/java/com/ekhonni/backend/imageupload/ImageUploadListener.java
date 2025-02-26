/**
 * Author: Rifat Shariar Sakil
 * Time: 2:10 PM
 * Date: 2/25/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.imageupload;

import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.ProductImage;
import com.ekhonni.backend.repository.CategoryRepository;
import com.ekhonni.backend.repository.ProductRepository;
import com.ekhonni.backend.util.CloudinaryImageUploadUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageUploadListener {



    private final CloudinaryImageUploadUtil cloudinaryImageUploadUtil;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    public ImageUploadListener(CloudinaryImageUploadUtil cloudinaryImageUploadUtil,
                               ProductRepository productRepository,
                               CategoryRepository categoryRepository) {
        this.cloudinaryImageUploadUtil = cloudinaryImageUploadUtil;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @RabbitListener(queues = "${rabbitmq-custom.image-upload-configuration.product-queue}")
    public void processImageUpload(ProductImageUploadEvent event) {
        try {
            Product product = productRepository.findById(event.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // byte[] to MultipartFile
            List<MultipartFile> multipartFiles = event.imageBytes().stream()
                    .map(bytes -> new InMemoryMultipartFile(bytes, event.filenames().get(event.imageBytes().indexOf(bytes)), event.contentTypes().get(event.imageBytes().indexOf(bytes))))
                    .collect(Collectors.toList());

            // upload images to Cloudinary
            List<String> imageUrls = cloudinaryImageUploadUtil.uploadImages(multipartFiles);


            // convert URLs to ProductImage & update product
            List<ProductImage> images = imageUrls.stream()
                    .map(ProductImage::new)
                    .collect(Collectors.toList());

            product.setImages(images);
            productRepository.save(product);


            System.out.println("iamge saved");

        } catch (Exception e) {
            System.err.println("Image upload failed for product ID: " + event.productId());
            e.printStackTrace();
        }
    }


    @RabbitListener(queues = "${rabbitmq-custom.image-upload-configuration.category-queue}")
    public void processImageUploadOfCategory(CategoryImageUploadEvent event) {
        try {
            Category category = categoryRepository.findById(event.categoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            // Convert byte[] to MultipartFile
            MultipartFile multipartFile = new InMemoryMultipartFile(
                    event.imageBytes(), event.filename(), event.contentType()
            );

            // Upload image to Cloudinary
            String imageUrl = cloudinaryImageUploadUtil.uploadImage(multipartFile);

            // Update category with the new image path
            category.setImagePath(imageUrl);
            categoryRepository.save(category);

        } catch (Exception e) {
            System.err.println("Image upload failed for category ID: " + event.categoryId());
            e.printStackTrace();
        }
    }

}
