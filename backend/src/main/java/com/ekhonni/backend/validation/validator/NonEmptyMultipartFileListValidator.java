/**
 * Author: Rifat Shariar Sakil
 * Time: 7:03 PM
 * Date: 1/10/2025
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.validation.validator;

import com.ekhonni.backend.validation.annotation.NonEmptyMultipartFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class NonEmptyMultipartFileListValidator implements ConstraintValidator<NonEmptyMultipartFile, List<MultipartFile>> {

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        if (files == null || files.isEmpty()) {
            return false;
        }
        return files.stream().allMatch(file -> file != null && !file.isEmpty());
    }
}
