package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.cart.AddToCartRequest;
import com.ecommerce.backend.dto.cart.CartResponse;
import com.ecommerce.backend.dto.cart.UpdateCartItemRequest;
import com.ecommerce.backend.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public CartResponse addToCart(
            @Valid @RequestBody AddToCartRequest request
    ) {

        return cartService.addToCart(request);
    }

    @GetMapping
    public CartResponse getCart() {

        return cartService.getCart();
    }

    @PutMapping("/update")
    public CartResponse updateQuantity(
            @Valid
            @RequestBody
            UpdateCartItemRequest request
    ) {

        return cartService.updateQuantity(request);
    }

    @DeleteMapping("/remove/{productId}")
    public CartResponse removeItem(
            @PathVariable Long productId
    ) {

        return cartService.removeItem(productId);
    }

    @DeleteMapping("/clear")
    public String clearCart() {

        return cartService.clearCart();
    }
}