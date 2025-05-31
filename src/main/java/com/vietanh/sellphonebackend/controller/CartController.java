package com.vietanh.sellphonebackend.controller;

import com.vietanh.sellphonebackend.dto.AddToCartRequest;
import com.vietanh.sellphonebackend.dto.ApiResponse;
import com.vietanh.sellphonebackend.dto.CartItemResponse;
import com.vietanh.sellphonebackend.dto.UpdateCartRequest;
import com.vietanh.sellphonebackend.service.CartService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {

    CartService cartService;

    @PostMapping
    public ApiResponse<Void> addToCart(@RequestBody AddToCartRequest request) {
        cartService.addToCart(request);
        return ApiResponse.<Void>builder()
                .message("Product added to cart successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<CartItemResponse>> getCartItems() {

        return ApiResponse.<List<CartItemResponse>>builder()
                .result(cartService.getCartItemsForCurrentUser())
                .build();
    }

    @DeleteMapping("/{productId}")
    public ApiResponse<Void> removeCartItem(@PathVariable int productId) {
        cartService.removeCartItem(productId);
        return ApiResponse.<Void>builder()
                .message("Cart item removed successfully")
                .build();
    }
}
