package com.vietanh.sellphonebackend.controller;

import com.vietanh.sellphonebackend.dto.ApiResponse;
import com.vietanh.sellphonebackend.dto.FavouriteProductResponse;
import com.vietanh.sellphonebackend.service.FavouriteService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favourite")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FavouriteController {

    FavouriteService favouriteService;

    @PostMapping("/{productId}")
    public ApiResponse<Void> addToFavorites(@PathVariable int productId) {
        favouriteService.addFavorite(productId);
        return ApiResponse.<Void>builder()
                .message("Product added to favorites successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<FavouriteProductResponse>> getFavorites() {
        return ApiResponse.<List<FavouriteProductResponse>>builder()
                .result(favouriteService.getFavorites())
                .build();
    }

    @DeleteMapping("/{favouriteId}")
    public ApiResponse<Void> removeFavorite(@PathVariable int favouriteId) {
        favouriteService.removeFavorite(favouriteId);
        return ApiResponse.<Void>builder()
                .message("Favorite removed successfully")
                .build();
    }
}
