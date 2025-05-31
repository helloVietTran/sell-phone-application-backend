package com.vietanh.sellphonebackend.repository;

import com.vietanh.sellphonebackend.entity.Product;
import com.vietanh.sellphonebackend.enums.Brand;
import com.vietanh.sellphonebackend.enums.Color;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> withFilters(Double maxPrice, List<Brand> brands, List<Integer> memories, Color color) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (maxPrice != null) {
                predicates.add(cb.lessThan(root.get("price"), maxPrice));
            }

            if (brands != null && !brands.isEmpty()) {
                predicates.add(root.get("brand").in(brands));
            }

            if (memories != null && !memories.isEmpty()) {
                predicates.add(root.get("memory").in(memories));
            }

            if (color != null) {
                predicates.add(cb.equal(root.get("color"), color));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
