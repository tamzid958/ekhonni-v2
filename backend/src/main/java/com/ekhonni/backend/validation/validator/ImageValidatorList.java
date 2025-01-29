/**
 * Author: Rifat Shariar Sakil
 * Time: 8:05 PM
 * Date: 1/10/2025
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.validation.validator;

import com.ekhonni.backend.validation.annotation.ImageOnly;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ImageValidatorList implements ConstraintValidator<ImageOnly, List<MultipartFile>> {

    @Override
    public boolean isValid(List<MultipartFile> images, ConstraintValidatorContext context) {

        if (images == null || images.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Image list must not be empty.")
                    .addConstraintViolation();
            return false;
        }

        ImageValidator singleFileValidator = new ImageValidator();
        for (MultipartFile image : images) {
            if (!singleFileValidator.isValid(image, context)) {
                return false;
            }
        }

        return true;
    }
}

