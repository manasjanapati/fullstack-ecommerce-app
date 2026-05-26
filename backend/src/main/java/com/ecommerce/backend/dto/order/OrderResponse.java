package com.ecommerce.backend.dto.order;

import com.ecommerce.backend.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {

    private Long orderId;

    private Double totalAmount;

    private OrderStatus status;

    private LocalDateTime createdAt;

    private List<OrderItemResponse> items;

    private String orderNumber;

    private String customerName;

    private String customerEmail;

    private String shippingAddress;

    private String phoneNumber;
}