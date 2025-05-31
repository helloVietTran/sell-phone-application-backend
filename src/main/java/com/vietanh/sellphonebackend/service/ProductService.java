package com.vietanh.sellphonebackend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.vietanh.sellphonebackend.dto.ProductRequest;
import com.vietanh.sellphonebackend.dto.ProductResponse;
import com.vietanh.sellphonebackend.entity.Product;
import com.vietanh.sellphonebackend.enums.Brand;
import com.vietanh.sellphonebackend.enums.Color;
import com.vietanh.sellphonebackend.enums.ProductType;
import com.vietanh.sellphonebackend.exception.AppException;
import com.vietanh.sellphonebackend.exception.ErrorCode;
import com.vietanh.sellphonebackend.mapper.ProductMapper;
import com.vietanh.sellphonebackend.repository.ProductRepository;
import com.vietanh.sellphonebackend.repository.ProductSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;
    Cloudinary cloudinary;

    public String uploadFile(MultipartFile file, String folderName) throws IOException {

        try {
            if (file == null || file.isEmpty()) { return null; }

            Map<?, ?> uploadOptions = ObjectUtils.asMap("folder", folderName);
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadOptions);

            return uploadResult.get("url").toString();
        } catch (IOException e) {
            throw new AppException(ErrorCode.UPLOAD_FILE_FAILED);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse createProduct(ProductRequest request) throws IOException {

        String imageUrl =  uploadFile(request.getFile(), "phones");

        Product product = productMapper.toProduct(request);
        if(!Objects.isNull(imageUrl)){
            product.setImageUrl(imageUrl);
        }

        Product savedProduct = productRepository.save(product);

        return productMapper.toProductResponse(savedProduct);
    }


    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse updateProduct(int productId, ProductRequest request) throws  IOException {
        String imageUrl =  uploadFile(request.getFile(), "phones");

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        if(!Objects.isNull(imageUrl)){
            existingProduct.setImageUrl(imageUrl);
        }

        productMapper.updateProduct(existingProduct, request);

        Product updatedProduct = productRepository.save(existingProduct);

        return productMapper.toProductResponse(updatedProduct);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(int productId) {

        if (!productRepository.existsById(productId)) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        productRepository.deleteById(productId);
    }

    public List<ProductResponse> getProductsByType(ProductType type) {
        List<Product> products = productRepository.findByType(type);
        return products.stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    public List<ProductResponse> filterProduct(Double maxPrice, List<Brand> brands, List<Integer> memories, Color color, String sortByPrice) {
        Specification<Product> spec = ProductSpecification.withFilters(maxPrice, brands, memories, color);

        Sort sort = Sort.unsorted();
        if ("asc".equalsIgnoreCase(sortByPrice)) {
            sort = Sort.by(Sort.Direction.ASC, "price");
        } else if ("dsc".equalsIgnoreCase(sortByPrice)) {
            sort = Sort.by(Sort.Direction.DESC, "price");
        }

        return productRepository.findAll(spec, sort).stream()
                .map(productMapper::toProductResponse)
                .toList();
    }


    public List<ProductResponse> search(String searchValue) {
        if (searchValue == null || searchValue.trim().isEmpty()) {
            return productRepository.findAll()
                    .stream()
                    .map(productMapper::toProductResponse)
                    .toList();
        }

        return productRepository.findByNameContainingIgnoreCase(searchValue)
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }


    public ProductResponse getProductById(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        return productMapper.toProductResponse(product);
    }

}
