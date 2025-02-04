package com.ekhonni.backend.dto;

import com.ekhonni.backend.enums.ReportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 * Date: 2/2/25
 */
public record ReportDTO(@NotNull(message = "Reported user ID cannot be null")
                        UUID reportedId,
                        @NotNull(message = "Report Type cannot be null")
                        ReportType type,
                        @NotBlank(message = "Details cannot be blank")
                        String details) {
}
