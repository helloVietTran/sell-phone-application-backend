package com.vietanh.sellphonebackend.service;

import com.vietanh.sellphonebackend.dto.OrderResponse;
import com.vietanh.sellphonebackend.entity.*;
import com.vietanh.sellphonebackend.enums.OrderStatus;
import com.vietanh.sellphonebackend.enums.VoucherCode;
import com.vietanh.sellphonebackend.exception.AppException;
import com.vietanh.sellphonebackend.exception.ErrorCode;
import com.vietanh.sellphonebackend.repository.*;
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
public class OrderService {
    CartItemRepository cartItemRepository;
    UserRepository userRepository;
    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;
    VoucherRepository voucherRepository;
    AuthenticationService authenticationService;

    public OrderResponse placeOrder() {
        int userId = authenticationService.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new AppException(ErrorCode.CART_EMPTY);
        }

        // Tính tổng giá gốc
        double totalPrice = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        // nếu đây là lần mua đầu tiên
        boolean isFirstOrder = orderRepository.countByUser(user) == 0;

        if (isFirstOrder) {

            Voucher voucher = voucherRepository.findByCodeAndActiveTrue(VoucherCode.FIRST_PURCHASE)
                    .orElse(null);

            if (voucher != null) {
                double discount = totalPrice * voucher.getDiscountPercent() / 100.0;
                totalPrice -= discount;
            }
        }

        // Lưu đơn hàng
        Order order = Order.builder()
                .user(user)
                .totalPrice(totalPrice + 15000)// 15.000 là phí ship
                .status(OrderStatus.PENDING)
                .createdAt(Instant.now())
                .build();

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> OrderItem.builder()
                        .order(savedOrder)
                        .product(cartItem.getProduct())
                        .quantity(cartItem.getQuantity())
                        .price(cartItem.getProduct().getPrice() * cartItem.getQuantity())
                        .build())
                .toList();

        orderItemRepository.saveAll(orderItems);

        cartItemRepository.deleteAll(cartItems);

        return OrderResponse.builder()
                .email(user.getEmail())
                .orderId(order.getId())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus().name())
                .createdAt(DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        .withZone(java.time.ZoneId.systemDefault())
                        .format(order.getCreatedAt())
                )
                .items(orderItems.stream().map(oi -> OrderResponse.OrderItemResponse.builder()
                        .productId(oi.getProduct().getId())
                        .productName(oi.getProduct().getName())
                        .quantity(oi.getQuantity())
                        .price(oi.getPrice())
                        .build()).toList())
                .build();
    }

    public List<OrderResponse> getOrders() {

        List<Order> orders = orderRepository.findAll();

        return orders.stream().map(order ->
                OrderResponse.builder()
                        .orderId(order.getId())
                        .email(order.getUser().getEmail())
                        .userFullName(order.getUser().getFullName())
                        .totalPrice(order.getTotalPrice())
                        .status(order.getStatus().name())
                        .createdAt(DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                .withZone(java.time.ZoneId.systemDefault())
                                .format(order.getCreatedAt())
                        )
                        .build()
        ).toList();
    }

    public void updateOrderStatus(int orderId, boolean accept) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new AppException(ErrorCode.ORDER_ALREADY_PROCESSED);
        }

        order.setStatus(accept ? OrderStatus.CONFIRMED : OrderStatus.CANCELED);
        orderRepository.save(order);
    }

}

