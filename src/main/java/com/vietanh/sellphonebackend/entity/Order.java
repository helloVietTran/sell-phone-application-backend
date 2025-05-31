package com.vietanh.sellphonebackend.entity;

import com.vietanh.sellphonebackend.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    User user;

    Double totalPrice;

    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @OneToMany
    List<OrderItem> items;

    Instant createdAt;
}
