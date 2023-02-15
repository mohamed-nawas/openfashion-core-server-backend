package com.computicsolutions.openfashion.exception;

/**
 * Product not found exception
 */
public class ProductNotFoundException extends RuntimeException {

    /**
     * Product not found exception with error msg
     *
     * @param message message
     */
    public ProductNotFoundException(String message) {
        super(message);
    }

    /**
     * Product not found exception with error msg and throwable error
     *
     * @param message message
     * @param error   error
     */
    public ProductNotFoundException(String message, Throwable error) {
        super(message, error);
    }
}
