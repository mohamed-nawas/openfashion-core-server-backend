package com.computicsolutions.openfashion.exception;

/**
 * OpenFashion core service exception
 */
public class OpenFashionCoreException extends RuntimeException {

    /**
     * OpenFashion core service exception with error msg
     *
     * @param message message
     */
    public OpenFashionCoreException(String message) {
        super(message);
    }

    /**
     * OpenFashion core service exception with error msg and throwable error
     *
     * @param message message
     * @param error   error
     */
    public OpenFashionCoreException(String message, Throwable error) {
        super(message, error);
    }
}
