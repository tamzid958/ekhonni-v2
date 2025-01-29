/**
 * Author: Rifat Shariar Sakil
 * Time: 5:13 PM
 * Date: 1/16/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class CloudinaryImageUploadUtil {
    @Autowired
    private Cloudinary cloudinary;

    public  List<String> uploadImages(List<MultipartFile> images) throws IOException {

        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile image : images) {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = uploadResult.get("url").toString();
            imageUrls.add(imageUrl);
        }

        return imageUrls;
    }
}
