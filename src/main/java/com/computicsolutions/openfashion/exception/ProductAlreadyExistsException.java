package com.computicsolutions.openfashion.exception;

/**
 * Product already exists exception
 */
public class ProductAlreadyExistsException extends RuntimeException {

    /**
     * Product already exists exception with error msg
     *
     * @param message message
     */
    public ProductAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Product already exists exception with error msg and throwable error
     *
     * @param message message
     * @param error   error
     */
    public ProductAlreadyExistsException(String message, Throwable error) {
        super(message, error);
    }
}
