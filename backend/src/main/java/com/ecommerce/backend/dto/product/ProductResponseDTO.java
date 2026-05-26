package com.ecommerce.backend.dto.product;

import com.ecommerce.backend.dto.category.CategoryResponseDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponseDTO {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private Integer stock;

    private String imageUrl;

    private CategoryResponseDTO category;

    private Double averageRating;

    private Integer ratingCount;
}