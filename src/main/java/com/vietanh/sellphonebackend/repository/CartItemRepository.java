package com.vietanh.sellphonebackend.repository;

import com.vietanh.sellphonebackend.entity.CartItem;
import com.vietanh.sellphonebackend.entity.Product;
import com.vietanh.sellphonebackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByUser(User user);
    Optional<CartItem> findByUserAndProduct(User user, Product product);
    void deleteByUser(User user);
}