package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.cart.*;
import com.ecommerce.backend.entity.*;
import com.ecommerce.backend.exception.ResourceNotFoundException;
import com.ecommerce.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartResponse addToCart(
            AddToCartRequest request
    ) {

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

        Product product =
                productRepository.findById(
                        request.getProductId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found"
                        ));
        
        if(request.getQuantity() > product.getStock()) {

            throw new RuntimeException(
                    "Insufficient stock available"
            );
        }

        Cart cart = cartRepository.findByUserId(
                user.getId()
        ).orElseGet(() -> {

            Cart newCart = Cart.builder()
                    .user(user)
                    .items(new ArrayList<>())
                    .build();

            return cartRepository.save(newCart);
        });

        CartItem cartItem =
                cartItemRepository
                        .findByCartIdAndProductId(
                                cart.getId(),
                                product.getId()
                        )
                        .orElse(null);

        if(cartItem != null) {

            cartItem.setQuantity(
                    cartItem.getQuantity()
                            + request.getQuantity()
            );

        } else {

            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();

            cart.getItems().add(cartItem);
        }

        cartItemRepository.save(cartItem);

        return mapToCartResponse(cart);
    }

    public CartResponse getCart() {

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

        return mapToCartResponse(cart);
    }

    private CartResponse mapToCartResponse(
            Cart cart
    ) {

        List<CartItemResponse> itemResponses =
                cart.getItems()
                        .stream()
                        .map(item -> {

                            double total =
                                    item.getProduct().getPrice()
                                            * item.getQuantity();

                            return CartItemResponse.builder()
                                    .productId(
                                            item.getProduct().getId()
                                    )
                                    .productName(
                                            item.getProduct().getName()
                                    )
                                    .price(
                                            item.getProduct().getPrice()
                                    )
                                    .quantity(
                                            item.getQuantity()
                                    )
                                    .totalPrice(total)
                                    .build();
                        })
                        .toList();

        double grandTotal =
                itemResponses.stream()
                        .mapToDouble(
                                CartItemResponse::getTotalPrice
                        )
                        .sum();

        return CartResponse.builder()
                .cartId(cart.getId())
                .items(itemResponses)
                .grandTotal(grandTotal)
                .build();
    }


    public CartResponse updateQuantity(
            UpdateCartItemRequest request
    ) {

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

        CartItem cartItem =
                cartItemRepository
                        .findByCartIdAndProductId(
                                cart.getId(),
                                request.getProductId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Cart item not found"
                                ));

        if(request.getQuantity()
                > cartItem.getProduct().getStock()) {

            throw new RuntimeException(
                    "Insufficient stock"
            );
        }

        cartItem.setQuantity(request.getQuantity());

        cartItemRepository.save(cartItem);

        return mapToCartResponse(cart);
    }


    public CartResponse removeItem(
            Long productId
    ) {

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

        CartItem cartItem =
                cartItemRepository
                        .findByCartIdAndProductId(
                                cart.getId(),
                                productId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Cart item not found"
                                ));

        cart.getItems().remove(cartItem);

        cartItemRepository.delete(cartItem);

        return mapToCartResponse(cart);
    }

    public String clearCart() {

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

        cart.getItems().clear();

        cartRepository.save(cart);

        return "Cart cleared successfully";
    }
}