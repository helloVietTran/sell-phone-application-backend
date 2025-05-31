package com.vietanh.sellphonebackend.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {
    int productId;
    String productName;
    double price;
    int quantity;
    String imageUrl;
    int ram;
    int memory;
}
