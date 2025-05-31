package com.vietanh.sellphonebackend.mapper;

import com.vietanh.sellphonebackend.dto.ProductRequest;
import com.vietanh.sellphonebackend.dto.ProductResponse;
import com.vietanh.sellphonebackend.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(ProductRequest request);

    ProductResponse toProductResponse(Product product);

    void updateProduct(@MappingTarget Product story, ProductRequest request);
}