package com.computicsolutions.openfashion.enums;

import lombok.Getter;

/**
 * Enum values for Success Response
 */
@Getter
public enum SuccessResponseStatusType {

    CREATE_USER(2000, "Successfully created the user"),
    CREATE_PRODUCT(2001, "Successfully created the product"),
    READ_PRODUCT(2002, "Successfully read the product"),
    FILE_UPLOAD(2003, "Successfully uploaded the file"),
    READ_FILE(2004, "Successfully read the file"),
    READ_PRODUCT_LIST(2005, "Successfully read the product list");

    private final int code;
    private final String message;

    SuccessResponseStatusType(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
