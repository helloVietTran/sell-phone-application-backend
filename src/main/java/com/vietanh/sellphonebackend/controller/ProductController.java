package com.vietanh.sellphonebackend.controller;

import com.vietanh.sellphonebackend.dto.ApiResponse;
import com.vietanh.sellphonebackend.dto.ProductRequest;
import com.vietanh.sellphonebackend.dto.ProductResponse;
import com.vietanh.sellphonebackend.enums.Brand;
import com.vietanh.sellphonebackend.enums.Color;
import com.vietanh.sellphonebackend.enums.ProductType;
import com.vietanh.sellphonebackend.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

   ProductService productService;

    @PostMapping
    public ApiResponse<ProductResponse> createProduct(@ModelAttribute ProductRequest request) throws IOException {

        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(request))
                .build();
    }

    @PutMapping("/{productId}")
    public ApiResponse<ProductResponse> updateProduct (
            @PathVariable int productId,
            @RequestBody ProductRequest request)  throws  IOException {

        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(productId, request))
                .build();
    }

    @DeleteMapping("/{productId}")
    public ApiResponse<Void> deleteProduct(@PathVariable int productId) {
        productService.deleteProduct(productId);

        return ApiResponse.<Void>builder()
                .message("Delete product successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<ProductResponse>> getProductsByType(@RequestParam ProductType type) {

        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getProductsByType(type))
                .build();
    }

    @GetMapping("/filter")
    public ApiResponse<List<ProductResponse>> filterProduct(
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) List<Brand> brands,
            @RequestParam(required = false) List<Integer> memories,
            @RequestParam(required = false) Color color,
            @RequestParam(required = false) String sortByPrice
    ) {

        return  ApiResponse.<List<ProductResponse>>builder()
                .result(productService.filterProduct(maxPrice, brands, memories, color, sortByPrice))
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<List<ProductResponse>> search(
            @RequestParam(required = false) String searchValue

    ) {
        return  ApiResponse.<List<ProductResponse>>builder()
                .result(productService.search(searchValue))
                .build();
    }

    @GetMapping("/{productId}")
    public ApiResponse<ProductResponse> getProductById(
            @PathVariable Integer productId

    ) {
        return  ApiResponse.<ProductResponse>builder()
                .result(productService.getProductById(productId))
                .build();
    }

}