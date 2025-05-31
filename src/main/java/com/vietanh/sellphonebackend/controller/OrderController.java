package com.vietanh.sellphonebackend.controller;

import com.vietanh.sellphonebackend.dto.ApiResponse;
import com.vietanh.sellphonebackend.dto.OrderResponse;
import com.vietanh.sellphonebackend.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

    OrderService orderService;

    @PostMapping
    public ApiResponse<OrderResponse> placeOrder() {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.placeOrder())
                .message("Order placed successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<OrderResponse>> getOrders() {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getOrders())
                .message("Fetched orders successfully")
                .build();
    }

    @PostMapping("/{orderId}/status")
    public ApiResponse<Void> updateOrderStatus(
            @PathVariable int orderId,
            @RequestParam boolean accept
    ) {
        orderService.updateOrderStatus(orderId, accept);
        String status = accept ? "confirmed" : "canceled";
        return ApiResponse.<Void>builder()
                .message("Order " + status + " successfully")
                .build();
    }

}
