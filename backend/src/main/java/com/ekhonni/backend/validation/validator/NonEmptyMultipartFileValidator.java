/**
 * Author: Rifat Shariar Sakil
 * Time: 2:33 PM
 * Date: 1/13/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.validation.validator;

import com.ekhonni.backend.validation.annotation.NonEmptyMultipartFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class NonEmptyMultipartFileValidator implements ConstraintValidator<NonEmptyMultipartFile, MultipartFile>{


    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        return multipartFile != null && !multipartFile.isEmpty();
    }
}
