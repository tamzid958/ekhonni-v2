package com.ekhonni.backend.dto;

import jakarta.validation.constraints.*;

public record BidLogDTO(@NotNull @Positive Double amount, String status) {

}
