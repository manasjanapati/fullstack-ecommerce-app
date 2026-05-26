package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.product.ProductRequestDTO;
import com.ecommerce.backend.dto.product.ProductResponseDTO;
import com.ecommerce.backend.dto.product.RatingRequest;
import com.ecommerce.backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping    
    public ProductResponseDTO createProduct(
            @Valid @RequestBody ProductRequestDTO dto
    ) {
        return productService.createProduct(dto);
    }

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(
            @PathVariable Long id
    ) {
        return productService.getProductById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ProductResponseDTO updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO dto
    ) {
        return productService.updateProduct(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteProduct(
            @PathVariable Long id
    ) {

        productService.deleteProduct(id);

        return "Product deleted successfully";
    }

    @GetMapping("/paged")
    public Page<ProductResponseDTO> getPaginatedProducts(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy
    ) {

        return productService.getPaginatedProducts(
                page,
                size,
                sortBy
        );
    }

    @GetMapping("/search")
    public Page<ProductResponseDTO> searchProducts(

            @RequestParam String keyword,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size
    ) {

        return productService.searchProducts(
                keyword,
                page,
                size
        );
    }


    @GetMapping("/category/{categoryId}")
    public Page<ProductResponseDTO> getProductsByCategory(

            @PathVariable Long categoryId,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size
    ) {

        return productService.getProductsByCategory(
                categoryId,
                page,
                size
        );
    }

    // @PostMapping("/{id}/rating")
    // public ProductResponseDTO
    // rateProduct(

    //         @PathVariable Long id,

    //         @Valid
    //         @RequestBody
    //         RatingRequest request
    // ) {

    //     return productService.rateProduct(

    //             id,

    //             request.getRating()
    //     );
    // }

    @PostMapping("/{id}/rating")
    public ProductResponseDTO
    rateProduct(

            @PathVariable Long id,

            @Valid
            @RequestBody
            RatingRequest request,

            Authentication authentication
    ) {

        return productService.rateProduct(

                id,

                request.getRating(),

                authentication.getName()
        );
    }

}