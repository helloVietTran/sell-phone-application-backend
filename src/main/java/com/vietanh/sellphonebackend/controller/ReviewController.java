package com.vietanh.sellphonebackend.controller;

import com.vietanh.sellphonebackend.dto.ApiResponse;
import com.vietanh.sellphonebackend.dto.ReviewRequest;
import com.vietanh.sellphonebackend.dto.ReviewResponse;
import com.vietanh.sellphonebackend.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewController {

    ReviewService reviewService;

    @PostMapping
    public ApiResponse<Void> createReview(@RequestBody ReviewRequest request) {
        reviewService.createReview(request);
        return ApiResponse.<Void>builder()
                .message("Review submitted successfully")
                .build();
    }

    @GetMapping("/{productId}")
    public ApiResponse<List<ReviewResponse>> getReviewsByProductId(@PathVariable int productId) {
        return ApiResponse.<List<ReviewResponse>>builder()
                .result(reviewService.getReviewsByProductId(productId))
                .build();
    }
}
