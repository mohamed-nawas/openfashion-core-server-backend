package com.computicsolutions.openfashion.controllers;

import com.computicsolutions.openfashion.enums.ErrorResponseStatusType;
import com.computicsolutions.openfashion.wrapper.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * Exception interceptor for file storage controller
 */
@Slf4j
@ControllerAdvice
public class FileStorageControllerExceptionAdvice extends Controller {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ResponseWrapper> handleMaxUploadSizeExceedException(Exception e) {
        log.error("File size for upload is exceeded", e);
        return getBadRequestResponse(ErrorResponseStatusType.FILE_SIZE_EXCEEDED);
    }
}
