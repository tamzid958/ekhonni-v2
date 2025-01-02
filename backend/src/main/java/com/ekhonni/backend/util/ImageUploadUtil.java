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
import java.util.UUID;

public class ImageUploadUtil {
    private ImageUploadUtil() {

    }

    public static String saveImage(String uploadDir, MultipartFile file) throws IOException {
        String imagePath = null;
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdir();
        }

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, filename);
        Files.write(filePath, file.getBytes());
        imagePath = filePath.toString();
        return imagePath;
    }
}
