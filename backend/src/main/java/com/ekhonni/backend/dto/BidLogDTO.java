package com.ekhonni.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record BidLogDTO(@NotNull @Positive Double amount, String status, UUID bidderId) {

}
