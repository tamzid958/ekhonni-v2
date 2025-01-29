package com.ekhonni.backend.dto;

import com.ekhonni.backend.validation.annotation.ImageOnly;
import org.springframework.web.multipart.MultipartFile;

/**
 * Author: Md Jahid Hasan
 * Date: 1/13/25
 */
public record ProfileImageDTO(@ImageOnly
                              MultipartFile image) {
}
