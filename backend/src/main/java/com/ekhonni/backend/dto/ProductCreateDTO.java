/**
 * Author: Rifat Shariar Sakil
 * Time: 12:50 AM
 * Date: 12/9/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto;

import com.ekhonni.backend.enums.ProductCondition;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ProductCreateDTO(
          @NotBlank
          String name,
          @Positive
          Double price,
          @NotBlank
          String description,
          @Enumerated(EnumType.STRING)
          ProductCondition condition,
          @NotBlank
          String category,
          @NotNull
          List<MultipartFile> images
) {

}


