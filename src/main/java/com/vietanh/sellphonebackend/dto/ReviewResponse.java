package com.vietanh.sellphonebackend.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewResponse {
    String userName;
    String content;
    Integer rating;
    String createdAt;
}
