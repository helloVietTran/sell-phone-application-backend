package com.vietanh.sellphonebackend.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(1999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),

    INVALID_TOKEN(1899, "Invalid token", HttpStatus.UNAUTHORIZED),

    PRODUCT_NOT_FOUND(1100, "Product is not exists", HttpStatus.NOT_FOUND),

    USER_NOT_FOUND(1200, "User is not exists", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS(1201, "User exists", HttpStatus.BAD_REQUEST),
    CART_ITEM_NOT_FOUND(1300, "Cart item is not exists", HttpStatus.NOT_FOUND),

    UPLOAD_FILE_FAILED(1400, "Upload file failed", HttpStatus.INTERNAL_SERVER_ERROR),

    ALREADY_LIKE_PHONE(1500, "You have liked this product", HttpStatus.BAD_REQUEST),
    FAVOURITE_NOT_FOUND(1501, "Favourite record is not exists", HttpStatus.BAD_REQUEST),

    CART_EMPTY(1502, "Your cart is empty", HttpStatus.BAD_REQUEST),
    ORDER_NOT_FOUND(1602, "Order  is not exists", HttpStatus.BAD_REQUEST),
    ORDER_ALREADY_PROCESSED(1602, "Order has already processed", HttpStatus.BAD_REQUEST),

    UNAUTHENTICATED(1900, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1901, "You do not have permission", HttpStatus.FORBIDDEN),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}