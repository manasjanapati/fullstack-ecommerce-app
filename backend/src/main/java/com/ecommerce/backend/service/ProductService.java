package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.category.CategoryResponseDTO;
import com.ecommerce.backend.dto.product.ProductRequestDTO;
import com.ecommerce.backend.dto.product.ProductResponseDTO;
import com.ecommerce.backend.entity.Category;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.exception.ResourceNotFoundException;
import com.ecommerce.backend.repository.CategoryRepository;
import com.ecommerce.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
// import com.ecommerce.backend.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductResponseDTO createProduct(ProductRequestDTO dto) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .imageUrl(dto.getImageUrl())
                .category(category)
                .active(true)
                .build();

        Product savedProduct = productRepository.save(product);

        return mapToDTO(savedProduct);
    }

    public List<ProductResponseDTO> getAllProducts() {

        return productRepository.findByActiveTrue()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private ProductResponseDTO mapToDTO(Product product) {

        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .imageUrl(product.getImageUrl())
                .averageRating(
                product.getAverageRating()
                )

                .ratingCount(
                product.getRatingCount()
                )
                .category(
                        CategoryResponseDTO.builder()
                                .id(product.getCategory().getId())
                                .name(product.getCategory().getName())
                                .build()
                )
                .build();
    }

    public ProductResponseDTO getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found"
                        ));

        return mapToDTO(product);
    }

    public ProductResponseDTO updateProduct(
            Long id,
            ProductRequestDTO dto
    ) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found"
                        ));

        Category category = categoryRepository.findById(
                dto.getCategoryId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Category not found"
                ));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setImageUrl(dto.getImageUrl());
        product.setCategory(category);

        Product updatedProduct = productRepository.save(product);

        return mapToDTO(updatedProduct);
    }    


        public String deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found"
                        ));

        product.setActive(false);

        productRepository.save(product);

        return "Product deactivated successfully";
        }


    public Page<ProductResponseDTO> getPaginatedProducts(
            int page,
            int size,
            String sortBy
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy)
        );

        return productRepository.findAll(pageable)
                .map(this::mapToDTO);
    }


    public Page<ProductResponseDTO> searchProducts(
            String keyword,
            int page,
            int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        return productRepository
                .findByNameContainingIgnoreCase(
                        keyword,
                        pageable
                )
                .map(this::mapToDTO);
    }

    public Page<ProductResponseDTO> getProductsByCategory(
            Long categoryId,
            int page,
            int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        return productRepository
                .findByCategoryId(categoryId, pageable)
                .map(this::mapToDTO);
    }

    public ProductResponseDTO
    rateProduct(

            Long productId,

            Integer rating,

            String email
    ) {

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found"
                                ));

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                ));

        if (
            user.getRatedProductIds()
                    .contains(productId)
        ) {

            throw new IllegalStateException(
                    "You already rated this product"
            );
        }

        double averageRating =
                product.getAverageRating() != null
                ? product.getAverageRating()
                : 0.0;

        int ratingCount =
                product.getRatingCount() != null
                ? product.getRatingCount()
                : 0;

        double currentTotal =
                averageRating * ratingCount;

        int newCount =
                ratingCount + 1;

        double newAverage =
                (currentTotal + rating)
                / newCount;

        product.setAverageRating(
                newAverage
        );

        product.setRatingCount(
                newCount
        );

        Product updatedProduct =
                productRepository.save(product);

        user.getRatedProductIds()
                .add(productId);

        userRepository.save(user);

        return mapToDTO(updatedProduct);
    }
}