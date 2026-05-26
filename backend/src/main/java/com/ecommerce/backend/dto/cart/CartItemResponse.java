package com.ecommerce.backend.dto.cart;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemResponse {

    private Long productId;

    private String productName;

    private Double price;

    private Integer quantity;

    private Double totalPrice;
}