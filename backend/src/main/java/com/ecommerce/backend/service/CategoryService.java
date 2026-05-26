package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.category.CategoryRequestDTO;
import com.ecommerce.backend.dto.category.CategoryResponseDTO;
import com.ecommerce.backend.entity.Category;
import com.ecommerce.backend.exception.ResourceNotFoundException;
import com.ecommerce.backend.repository.CategoryRepository;
import com.ecommerce.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryResponseDTO createCategory(
            CategoryRequestDTO dto
    ) {
        Category parentCategory = null;

        if (
            dto.getParentCategoryId()
            != null
        ) {

            parentCategory =
                    categoryRepository
                            .findById(
                                dto.getParentCategoryId()
                            )
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Parent category not found"
                                    ));
        }

        Category category =
                Category.builder()
                        .name(dto.getName())
                        .parentCategory(
                                parentCategory
                        )
                        .build();

        Category savedCategory =
                categoryRepository.save(category);

        return mapToDTO(savedCategory);
    }

    public List<CategoryResponseDTO> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public CategoryResponseDTO getCategoryById(
            Long id
    ) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found"
                        ));

        return mapToDTO(category);
    }

    public CategoryResponseDTO updateCategory(
            Long id,
            CategoryRequestDTO dto
    ) {

        Category category =
                categoryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Category not found"
                                ));

        Category parentCategory = null;

        if (
            dto.getParentCategoryId()
            != null
        ) {

            parentCategory =
                    categoryRepository
                            .findById(
                                    dto.getParentCategoryId()
                            )
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Parent category not found"
                                    ));
        }

        category.setName(dto.getName());

        category.setParentCategory(
                parentCategory
        );

        Category updatedCategory =
                categoryRepository.save(category);

        return mapToDTO(updatedCategory);
    }

    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found"
                        ));

        boolean hasProducts =
                productRepository.findByCategoryId(
                        id,
                        org.springframework.data.domain.Pageable.unpaged()
                ).hasContent();

        if(hasProducts) {
            throw new RuntimeException(
                    "Cannot delete category with products"
            );
        }

        categoryRepository.delete(category);
    }

    private CategoryResponseDTO mapToDTO(
            Category category
    ) {

        return CategoryResponseDTO.builder()

                .id(category.getId())

                .name(category.getName())

                .parentCategoryId(

                        category.getParentCategory()
                        != null

                        ? category.getParentCategory()
                                .getId()

                        : null
                )

                .parentCategoryName(

                        category.getParentCategory()
                        != null

                        ? category.getParentCategory()
                                .getName()

                        : null
                )

                .build();
    }
}