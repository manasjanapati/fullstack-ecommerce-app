package com.ecommerce.backend.config;

import com.ecommerce.backend.entity.Category;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.repository.CategoryRepository;
import com.ecommerce.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {

        if(categoryRepository.count() == 0) {

            Category electronics = categoryRepository.save(
                    Category.builder()
                            .name("Electronics")
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("iPhone 15")
                            .description("Apple smartphone")
                            .price(79999.0)
                            .stock(10)
                            .imageUrl("iphone.jpg")
                            .category(electronics)
                            .build()
            );

            productRepository.save(
                    Product.builder()
                            .name("Samsung S25")
                            .description("Samsung flagship")
                            .price(69999.0)
                            .stock(15)
                            .imageUrl("samsung.jpg")
                            .category(electronics)
                            .build()
            );
        }
    }
}