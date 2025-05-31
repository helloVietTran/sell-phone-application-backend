package com.vietanh.sellphonebackend.repository;


import com.vietanh.sellphonebackend.entity.FavoriteProduct;
import com.vietanh.sellphonebackend.entity.Product;
import com.vietanh.sellphonebackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Integer> {
    List<FavoriteProduct> findByUser(User user);
    Optional<FavoriteProduct> findByUserAndProduct(User user, Product product);
    void deleteByUserAndProduct(User user, Product product);

}