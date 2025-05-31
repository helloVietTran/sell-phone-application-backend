package com.vietanh.sellphonebackend.repository;

import com.vietanh.sellphonebackend.entity.Order;
import com.vietanh.sellphonebackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser(User user);

    long countByUser(User user);
}
