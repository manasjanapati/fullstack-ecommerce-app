package com.ecommerce.backend.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CheckoutRequest {

    @NotBlank
    private String shippingAddress;

    @NotBlank
    private String phoneNumber;
}