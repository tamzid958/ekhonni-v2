/**
 * Author: Rifat Shariar Sakil
 * Time: 3:50 PM
 * Date: 1/10/2025
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.validation.validator;

import com.ekhonni.backend.exception.ImageUploadException;
import com.ekhonni.backend.validation.annotation.ImageOnly;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class ImageValidator implements ConstraintValidator<ImageOnly, MultipartFile> {

    private static final List<String> SUPPORTED_CONTENT_TYPES = Arrays.asList(
            "image/jpeg", "image/jpg", "image/png", "image/gif", "image/bmp", "image/webp"
    );
    private static final List<String> SUPPORTED_EXTENSIONS = Arrays.asList(
            ".jpg", ".jpeg", ".png", ".gif", ".bmp", ".webp"
    );

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        try {
            validateFile(file);
        } catch (ImageUploadException e) {
            setConstraintViolationMessage(context, e.getMessage());
            return false;
        } catch (Exception e) {
            setConstraintViolationMessage(context, "An unexpected error occurred while validating the file.");
            return false;
        }
        return true;
    }

    private void validateFile(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new ImageUploadException("Image must not be null or empty.");
        }

        String contentType = image.getContentType();
        if (contentType == null || !SUPPORTED_CONTENT_TYPES.contains(contentType.toLowerCase())) {
            throw new ImageUploadException("Invalid image type. Only JPEG, PNG, GIF, BMP, and WEBP files are supported.");
        }

        String filename = image.getOriginalFilename();
        if (filename == null || SUPPORTED_EXTENSIONS.stream().noneMatch(filename.toLowerCase()::endsWith)) {
            throw new ImageUploadException("Invalid image extension. Only .jpg, .jpeg, .png, .gif, .bmp, and .webp are allowed.");
        }
    }

    private void setConstraintViolationMessage(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}


