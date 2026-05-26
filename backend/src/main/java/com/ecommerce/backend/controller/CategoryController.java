package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.category.CategoryRequestDTO;
import com.ecommerce.backend.dto.category.CategoryResponseDTO;
import com.ecommerce.backend.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public CategoryResponseDTO createCategory(
            @Valid @RequestBody CategoryRequestDTO dto
    ) {

        return categoryService.createCategory(dto);
    }

    @GetMapping
    public List<CategoryResponseDTO> getAllCategories() {

        return categoryService.getAllCategories();
    }


    @GetMapping("/{id}")
    public CategoryResponseDTO getCategoryById(
            @PathVariable Long id
    ) {

        return categoryService.getCategoryById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public CategoryResponseDTO updateCategory(

            @PathVariable Long id,

            @Valid @RequestBody CategoryRequestDTO dto
    ) {

        return categoryService.updateCategory(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteCategory(
            @PathVariable Long id
    ) {

        categoryService.deleteCategory(id);

        return "Category deleted successfully";
    }
}