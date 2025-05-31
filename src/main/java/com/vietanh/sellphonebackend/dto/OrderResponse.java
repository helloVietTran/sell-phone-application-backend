package com.vietanh.sellphonebackend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderResponse {
    int orderId;
    Double totalPrice;
    String status;
    String createdAt;
    List<OrderItemResponse> items;
    String email;
    String userFullName;

    @Data
    @Builder
    public static class OrderItemResponse {
        int productId;
        String productName;
        int quantity;
        double price;
    }
}
