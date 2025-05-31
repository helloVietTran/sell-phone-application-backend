package com.vietanh.sellphonebackend.service;

import com.vietanh.sellphonebackend.dto.ReviewRequest;
import com.vietanh.sellphonebackend.dto.ReviewResponse;
import com.vietanh.sellphonebackend.entity.Product;
import com.vietanh.sellphonebackend.entity.Review;
import com.vietanh.sellphonebackend.entity.User;
import com.vietanh.sellphonebackend.exception.AppException;
import com.vietanh.sellphonebackend.exception.ErrorCode;
import com.vietanh.sellphonebackend.repository.ProductRepository;
import com.vietanh.sellphonebackend.repository.ReviewRepository;
import com.vietanh.sellphonebackend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewService {
    AuthenticationService authenticationService;
    ReviewRepository reviewRepository;
    ProductRepository productRepository;
    UserRepository userRepository;

    public void createReview(ReviewRequest request) {
        int userId = authenticationService.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        int newRating = request.getRating();


        int currentCount = product.getRatingCount() != null ? product.getRatingCount() : 0;
        float currentAvg = product.getRatingPoint() != null ? product.getRatingPoint() : 0f;

        int newCount = currentCount + 1;
        float newAvg = ((currentAvg * currentCount) + newRating) / newCount;

        product.setRatingCount(newCount);
        product.setRatingPoint(newAvg);
        productRepository.save(product);

        Review review = Review.builder()
                .user(user)
                .product(product)
                .content(request.getContent())
                .rating(newRating)
                .createdAt(Instant.now())
                .build();

        reviewRepository.save(review);
    }


    public List<ReviewResponse> getReviewsByProductId(int productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        List<Review> reviews = reviewRepository.findByProduct(product);

        return reviews.stream()
                .map(r -> ReviewResponse.builder()
                        .userName(r.getUser().getFullName())
                        .content(r.getContent())
                        .rating(r.getRating())
                        .createdAt(DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                .withZone(java.time.ZoneId.systemDefault())
                                .format(r.getCreatedAt())
                        )
                        .build())
                .toList();
    }

}
