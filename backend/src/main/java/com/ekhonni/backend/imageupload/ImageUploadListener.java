/**
 * Author: Rifat Shariar Sakil
 * Time: 2:10 PM
 * Date: 2/25/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.imageupload;

import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.ProductImage;
import com.ekhonni.backend.repository.ProductRepository;
import com.ekhonni.backend.util.CloudinaryImageUploadUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageUploadListener {

    private final CloudinaryImageUploadUtil cloudinaryImageUploadUtil;
    private final ProductRepository productRepository;

    public ImageUploadListener(CloudinaryImageUploadUtil cloudinaryImageUploadUtil, ProductRepository productRepository) {
        this.cloudinaryImageUploadUtil = cloudinaryImageUploadUtil;
        this.productRepository = productRepository;
    }

    @RabbitListener(queues = "image_upload_queue")
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

        } catch (Exception e) {
            System.err.println("Image upload failed for product ID: " + event.productId());
            e.printStackTrace();
        }
    }
}
