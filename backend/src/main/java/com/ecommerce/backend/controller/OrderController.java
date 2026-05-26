package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.order.CheckoutRequest;
import com.ecommerce.backend.dto.order.OrderResponse;
import com.ecommerce.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.backend.dto.order.UpdateOrderStatusRequest;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public OrderResponse checkout(
        @Valid
        @RequestBody
        CheckoutRequest request) {

        return orderService.checkout(request);
    }

    @GetMapping
    public List<OrderResponse> getMyOrders() {

        return orderService.getMyOrders();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public List<OrderResponse> getAllOrders() {

        return orderService.getAllOrders();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{orderId}/status")
    public OrderResponse updateOrderStatus(

            @PathVariable Long orderId,

            @Valid
            @RequestBody
            UpdateOrderStatusRequest request
    ) {

        return orderService.updateOrderStatus(
                orderId,
                request
        );
    }

    
}