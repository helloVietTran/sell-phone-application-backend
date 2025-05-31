package com.vietanh.sellphonebackend.repository;

import com.vietanh.sellphonebackend.entity.Product;
import com.vietanh.sellphonebackend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByProduct(Product product);
}
