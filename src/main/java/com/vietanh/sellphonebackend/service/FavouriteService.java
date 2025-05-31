package com.vietanh.sellphonebackend.service;

import com.vietanh.sellphonebackend.dto.FavouriteProductResponse;
import com.vietanh.sellphonebackend.entity.FavoriteProduct;
import com.vietanh.sellphonebackend.entity.Product;
import com.vietanh.sellphonebackend.entity.User;
import com.vietanh.sellphonebackend.exception.AppException;
import com.vietanh.sellphonebackend.exception.ErrorCode;
import com.vietanh.sellphonebackend.repository.FavoriteProductRepository;
import com.vietanh.sellphonebackend.repository.ProductRepository;
import com.vietanh.sellphonebackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FavouriteService {

    FavoriteProductRepository favoriteProductRepository;
    ProductRepository productRepository;
    UserRepository userRepository;
    AuthenticationService authenticationService;

    public void addFavorite(int productId) {
        int userId = authenticationService.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        favoriteProductRepository.findByUserAndProduct(user, product).ifPresentOrElse(
                f -> { throw new AppException(ErrorCode.ALREADY_LIKE_PHONE); },
                () -> {
                    FavoriteProduct favorite = FavoriteProduct.builder()
                            .user(user)
                            .product(product)
                            .build();
                    favoriteProductRepository.save(favorite);
                }
        );
    }

    public List<FavouriteProductResponse> getFavorites() {
        int userId = authenticationService.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        List<FavoriteProduct> favorites = favoriteProductRepository.findByUser(user);

        return favorites.stream().map(f -> FavouriteProductResponse.builder()
                .favouriteId(f.getId())
                .productId(f.getProduct().getId())
                .productName(f.getProduct().getName())
                .imageUrl(f.getProduct().getImageUrl())
                .productDescription(f.getProduct().getDescription())
                .price(f.getProduct().getPrice())
                .build()
        ).toList();
    }

    public void removeFavorite(int favoriteId) {
        FavoriteProduct favorite = favoriteProductRepository.findById(favoriteId)
                .orElseThrow(() -> new AppException(ErrorCode.FAVOURITE_NOT_FOUND));
        favoriteProductRepository.delete(favorite);
    }
}
