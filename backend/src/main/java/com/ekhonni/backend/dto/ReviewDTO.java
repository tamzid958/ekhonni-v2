package com.ekhonni.backend.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Author: Md Sakil Ahmed
 * Date: 06/01/25
 */

public record ReviewDTO (@NotNull UUID buyerId,@NotNull UUID sellerId,@NotNull Long bidId,@NotNull Integer rating,String comment){
}
