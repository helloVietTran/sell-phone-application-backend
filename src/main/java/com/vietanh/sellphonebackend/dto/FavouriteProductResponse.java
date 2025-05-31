package com.vietanh.sellphonebackend.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FavouriteProductResponse {
    int favouriteId;
    int productId;
    String productName;
    String imageUrl;
    String productDescription;
    double price;
}

