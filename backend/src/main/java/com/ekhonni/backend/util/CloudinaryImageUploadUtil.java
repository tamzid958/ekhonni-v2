/**
 * Author: Rifat Shariar Sakil
 * Time: 5:13 PM
 * Date: 1/16/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ekhonni.backend.exception.ImageUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CloudinaryImageUploadUtil {
    @Autowired
    private Cloudinary cloudinary;

    public List<String> uploadImages(List<MultipartFile> images) throws IOException {

        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile image : images) {
            imageUrls.add(uploadImage(image));
        }

        return imageUrls;
    }

    public String uploadImage(MultipartFile image)  {
        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            throw new ImageUploadException("Failed to upload image");
        }
    }
}
