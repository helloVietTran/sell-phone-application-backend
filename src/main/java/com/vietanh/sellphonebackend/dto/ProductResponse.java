package com.vietanh.sellphonebackend.dto;

import com.vietanh.sellphonebackend.enums.Brand;
import com.vietanh.sellphonebackend.enums.Color;
import com.vietanh.sellphonebackend.enums.ProductType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    int id;
    String name;
    String description;
    Integer memory;
    Integer ram;
    Color color;
    Double price;
    Integer stock;
    String imageUrl;
    ProductType type;
    Brand brand;

    Integer ratingCount;
    Float ratingPoint;
}
