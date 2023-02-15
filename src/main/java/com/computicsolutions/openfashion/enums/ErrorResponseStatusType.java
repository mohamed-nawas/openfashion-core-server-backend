package com.computicsolutions.openfashion.enums;

import lombok.Getter;

/**
 * Enum values for Error Response
 */
@Getter
public enum ErrorResponseStatusType {

    MISSING_REQUIRED_FIELDS(4001, "Missing required fields"),
    ADMIN_ALREADY_EXISTS(4002, "Admin already exists in the system"),
    PRODUCT_ALREADY_EXISTS(4003, "Product already exists in the DB, Please do update OP"),
    INVALID_FILE_UPLOAD_NAME(4004, "Name for file upload is invalid"),
    INVALID_FILE_TYPE(4005, "File type is invalid"),
    FILE_SIZE_EXCEEDED(4006, "File size exceeded"),
    FILE_NOT_FOUND(4007, "File not found"),
    PRODUCT_NOT_FOUND(4008, "Product not found"),
    INTERNAL_SERVER_ERROR(5000, "Internal Server Error");

    private final int code;
    private final String message;

    ErrorResponseStatusType(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
