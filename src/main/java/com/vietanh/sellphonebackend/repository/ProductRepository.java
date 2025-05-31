package com.vietanh.sellphonebackend.repository;

import com.vietanh.sellphonebackend.entity.Product;
import com.vietanh.sellphonebackend.enums.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    List<Product> findByType(ProductType type);

    List<Product> findByNameContainingIgnoreCase(String searchValue);

}