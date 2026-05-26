package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.order.*;
import com.ecommerce.backend.entity.*;
import com.ecommerce.backend.exception.ResourceNotFoundException;
import com.ecommerce.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public OrderResponse checkout(CheckoutRequest request) {
        String orderNumber = "ORD-" + System.currentTimeMillis();

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        Cart cart = cartRepository.findByUserId(
                user.getId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Cart not found"
                ));

        if(cart.getItems().isEmpty()) {
            throw new RuntimeException(
                    "Cart is empty"
            );
        }

        double totalAmount = 0;

        List<OrderItem> orderItems =
                new ArrayList<>();

        for(CartItem cartItem : cart.getItems()) {

            Product product = cartItem.getProduct();

            if(cartItem.getQuantity()
                    > product.getStock()) {

                throw new RuntimeException(
                        product.getName()
                                + " is out of stock"
                );
            }

            product.setStock(
                    product.getStock()
                            - cartItem.getQuantity()
            );

            productRepository.save(product);

            double itemTotal =
                    product.getPrice()
                            * cartItem.getQuantity();

            totalAmount += itemTotal;

            OrderItem orderItem =
                    OrderItem.builder()
                            .product(product)
                            .quantity(
                                    cartItem.getQuantity()
                            )
                            .price(product.getPrice())
                            .build();

            orderItems.add(orderItem);
        }

        Order order = Order.builder()
                .user(user)
                .orderNumber(orderNumber)
                .customerName(user.getName())
                .customerEmail(user.getEmail())
                .shippingAddress(
                        request.getShippingAddress()
                )
                .phoneNumber(
                        request.getPhoneNumber()
                )
                .totalAmount(totalAmount)
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .items(new ArrayList<>())
                .build();

        Order savedOrder =
                orderRepository.save(order);

        for(OrderItem item : orderItems) {

            item.setOrder(savedOrder);

            orderItemRepository.save(item);
        }

        savedOrder.setItems(orderItems);

        cart.getItems().clear();

        cartRepository.save(cart);

        return mapToOrderResponse(savedOrder);
    }

    public List<OrderResponse> getMyOrders() {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        return orderRepository.findByUserId(
                user.getId()
        ).stream()
                .map(this::mapToOrderResponse)
                .toList();
    }

    private OrderResponse mapToOrderResponse(
            Order order
    ) {

        List<OrderItemResponse> itemResponses =
                order.getItems()
                        .stream()
                        .map(item -> {

                            double total =
                                    item.getPrice()
                                            * item.getQuantity();

                            return OrderItemResponse.builder()

                                    .productId(
                                            item.getProduct().getId()
                                    )

                                    .productName(
                                            item.getProduct().getName()
                                    )

                                    .quantity(
                                            item.getQuantity()
                                    )
                                    .imageUrl(
                                            item.getProduct().getImageUrl()
                                    )

                                    .price(item.getPrice())

                                    .totalPrice(total)

                                    .build();
                        })
                        .toList();

        return OrderResponse.builder()
                .orderId(order.getId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .items(itemResponses)
                .orderNumber(order.getOrderNumber())
                .customerName(order.getCustomerName())
                .customerEmail(order.getCustomerEmail())
                .shippingAddress(order.getShippingAddress())
                .phoneNumber(order.getPhoneNumber())
                .build();
    }

    public List<OrderResponse> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(this::mapToOrderResponse)
                .toList();
    }

    public OrderResponse updateOrderStatus(
            Long orderId,
            UpdateOrderStatusRequest request
    ) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found"
                        ));

        order.setStatus(request.getStatus());

        Order updatedOrder =
                orderRepository.save(order);

        return mapToOrderResponse(updatedOrder);
    }
}