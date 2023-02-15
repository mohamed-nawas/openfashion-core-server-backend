package com.computicsolutions.openfashion.exception;

/**
 * Upload file not found exception
 */
public class UploadFileNotFoundException extends RuntimeException {

    /**
     * Upload file not found exception with error msg
     *
     * @param message message
     */
    public UploadFileNotFoundException(String message) {
        super(message);
    }

    /**
     * Upload file not found exception with error msg and throwable error
     *
     * @param message message
     * @param error   error
     */
    public UploadFileNotFoundException(String message, Throwable error) {
        super(message, error);
    }
}
