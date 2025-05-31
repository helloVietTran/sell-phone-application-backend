package com.vietanh.sellphonebackend.service;

import com.vietanh.sellphonebackend.dto.AddToCartRequest;
import com.vietanh.sellphonebackend.dto.CartItemResponse;
import com.vietanh.sellphonebackend.dto.UpdateCartRequest;
import com.vietanh.sellphonebackend.entity.CartItem;
import com.vietanh.sellphonebackend.entity.Product;
import com.vietanh.sellphonebackend.entity.User;
import com.vietanh.sellphonebackend.exception.AppException;
import com.vietanh.sellphonebackend.exception.ErrorCode;
import com.vietanh.sellphonebackend.repository.CartItemRepository;
import com.vietanh.sellphonebackend.repository.ProductRepository;
import com.vietanh.sellphonebackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartService {
    CartItemRepository cartItemRepository;
    ProductRepository productRepository;
    UserRepository userRepository;
    AuthenticationService authenticationService;

    public void addToCart(AddToCartRequest request) {
        int userId = authenticationService.getCurrentUserId();


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product)
                .orElse(CartItem.builder().user(user).product(product).quantity(0).build());

        cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());

        cartItemRepository.save(cartItem);
    }

    public List<CartItemResponse> getCartItemsForCurrentUser() {
        int userId = authenticationService.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        List<CartItem> items = cartItemRepository.findByUser(user);

        return items.stream()
                .map(item -> CartItemResponse.builder()
                        .productId(item.getProduct().getId())
                        .imageUrl(item.getProduct().getImageUrl())
                        .productName(item.getProduct().getName())
                        .price(item.getProduct().getPrice() * item.getQuantity())
                        .quantity(item.getQuantity())
                        .ram(item.getProduct().getRam())
                        .memory(item.getProduct().getMemory())
                        .build())
                .toList();
    }



    public void removeCartItem(int productId) {
        int userId = authenticationService.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        cartItemRepository.delete(cartItem);
    }
}
