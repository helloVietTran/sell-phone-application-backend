package com.vietanh.sellphonebackend.entity;

import com.vietanh.sellphonebackend.enums.Brand;
import com.vietanh.sellphonebackend.enums.Color;
import com.vietanh.sellphonebackend.enums.ProductType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    Integer stock;

    String name;
    String description;
    Double price;

    Integer memory;
    Integer ram;

    String imageUrl;

    Integer ratingCount;
    Float ratingPoint;

    @Enumerated(EnumType.STRING)
    Color color;

    @Enumerated(EnumType.STRING)
    Brand brand;

    @Enumerated(EnumType.STRING)
    ProductType type;
}
