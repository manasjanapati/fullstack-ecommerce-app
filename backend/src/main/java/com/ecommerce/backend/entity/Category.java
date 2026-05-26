package com.ecommerce.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parentCategory;
}