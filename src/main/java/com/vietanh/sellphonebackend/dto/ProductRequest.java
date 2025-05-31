package com.vietanh.sellphonebackend.dto;

import com.vietanh.sellphonebackend.enums.Brand;
import com.vietanh.sellphonebackend.enums.Color;
import com.vietanh.sellphonebackend.enums.ProductType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    String name;
    String description;
    Integer memory;
    Integer ram;
    Color color;
    Double price;
    Integer stock;

    MultipartFile file;
    ProductType type;
    Brand brand;
}
