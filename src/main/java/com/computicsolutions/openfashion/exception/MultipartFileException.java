package com.computicsolutions.openfashion.exception;

/**
 * Multipart file exception
 */
public class MultipartFileException extends RuntimeException {

    /**
     * Multipart file exception with error msg
     *
     * @param message message
     */
    public MultipartFileException(String message) {
        super(message);
    }

    /**
     * Multipart file exception with error msg and throwable error
     *
     * @param message message
     * @param error   error
     */
    public MultipartFileException(String message, Throwable error) {
        super(message, error);
    }
}
