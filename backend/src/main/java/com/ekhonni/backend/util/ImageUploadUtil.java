/**
 * Author: Rifat Shariar Sakil
 * Time: 11:46 AM
 * Date: 1/2/2025
 * Project Name: backend
 */

package com.ekhonni.backend.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ImageUploadUtil {

    public static List<String> saveImage(String uploadDir, List<MultipartFile> images) throws IOException {
        List<String> imagePaths = new ArrayList<>();
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdir();
        }

        for (MultipartFile image : images) {
            String imagePath = null;
            String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, filename);
            Files.write(filePath, image.getBytes());
            //imagePath = filePath.toString();
            imagePaths.add(filePath.getFileName().toString());
        }
        return imagePaths;
    }
}
