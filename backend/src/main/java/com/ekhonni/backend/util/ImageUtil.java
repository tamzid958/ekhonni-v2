/**
 * Author: Rifat Shariar Sakil
 * Time: 11:46 AM
 * Date: 1/2/2025
 * Project Name: backend
 */

package com.ekhonni.backend.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageUtil {

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

    public Path generateFilePath(String uploadDir, MultipartFile image) {
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdir();
        }
        String fileName = UUID.randomUUID() + "-" + Objects.requireNonNull(image.getOriginalFilename()).replaceAll(" ", "-");
        return Paths.get(uploadDir, fileName);
    }

    public void saveToPath(Path filePath, MultipartFile image) throws IOException {
        Files.write(filePath, image.getBytes());
    }
}
