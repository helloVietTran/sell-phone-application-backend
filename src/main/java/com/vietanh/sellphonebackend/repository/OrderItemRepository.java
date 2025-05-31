package com.vietanh.sellphonebackend.repository;

import com.vietanh.sellphonebackend.entity.Order;
import com.vietanh.sellphonebackend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrder(Order order);
}