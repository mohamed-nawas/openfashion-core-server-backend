package com.computicsolutions.openfashion.controllers;

import com.computicsolutions.openfashion.dto.response.FileUploadResponseDto;
import com.computicsolutions.openfashion.entity.File;
import com.computicsolutions.openfashion.enums.ErrorResponseStatusType;
import com.computicsolutions.openfashion.enums.FileType;
import com.computicsolutions.openfashion.enums.SuccessResponseStatusType;
import com.computicsolutions.openfashion.exception.OpenFashionCoreException;
import com.computicsolutions.openfashion.exception.UploadFileNotFoundException;
import com.computicsolutions.openfashion.service.FileStorageService;
import com.computicsolutions.openfashion.wrapper.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * File Storage Controller
 */
@RestController
@RequestMapping("api/v1/fileStorage")
@Slf4j
public class FileStorageController extends Controller {

    private final FileStorageService fileStorageService;

    @Autowired
    public FileStorageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    /**
     * This method uploads a file to the DB and returns the corresponding file data
     *
     * @param uploadFileName optional file name to upload
     * @param request        multipart http servlet request
     * @return file response data
     */
    @PostMapping(path = "/upload", consumes = MULTIPART_FORM_DATA, produces = APPLICATION_JSON_UTF_8)
    public ResponseEntity<ResponseWrapper> uploadFile(@RequestParam(required = false) String uploadFileName,
                                                      MultipartHttpServletRequest request) {
        if (request.getFile("file") == null || Objects.requireNonNull(request.getFile("file"))
                .getSize() <= 0) {
            log.error("Uploaded file does not exists");
            return getBadRequestResponse(ErrorResponseStatusType.MISSING_REQUIRED_FIELDS);
        }
        if (uploadFileName != null && !isValidUploadName(uploadFileName)) {
            log.error("File upload name is not valid");
            return getBadRequestResponse(ErrorResponseStatusType.INVALID_FILE_UPLOAD_NAME);
        }
        MultipartFile file = request.getFile("file");
        assert file != null;
        String filename = file.getOriginalFilename();
        try {
            String fileType = file.getContentType();
            if (fileType == null || !isAllowedFileType(fileType)) {
                log.error("File type for upload is invalid");
                return getBadRequestResponse(ErrorResponseStatusType.INVALID_FILE_TYPE);
            }
            File fileDB = fileStorageService.uploadFile(file, uploadFileName);
            FileUploadResponseDto fileUploadResponseDto = new FileUploadResponseDto(fileDB);
            log.debug("Successfully uploaded file: {}, fileId: {}", fileUploadResponseDto.toLogJson(), fileDB.getId());
            return getSuccessResponse(SuccessResponseStatusType.FILE_UPLOAD, fileUploadResponseDto);
        } catch (OpenFashionCoreException e) {
            log.error("Uploading file to the database was failed for imageName: {}", filename, e);
            return getInternalServerErrorResponse();
        }
    }

    /**
     * This method returns a file by id
     *
     * @param fileId file id
     * @return success(File response)/ error response
     */
    @GetMapping(path = "/download/{fileId}", produces = APPLICATION_JSON_UTF_8)
    public ResponseEntity<ResponseWrapper> getFileById(@PathVariable("fileId") String fileId) {
        try {
            File file = fileStorageService.getFileById(fileId);
            FileUploadResponseDto responseDto = new FileUploadResponseDto(file);
            log.debug("Successfully returned the file for fileId: {}", fileId);
            return getSuccessResponse(SuccessResponseStatusType.READ_FILE, responseDto);
        } catch (UploadFileNotFoundException e) {
            log.error("Getting file from the DB was failed for fileId: {}", fileId, e);
            return getBadRequestResponse(ErrorResponseStatusType.FILE_NOT_FOUND);
        } catch (OpenFashionCoreException e) {
            log.error("Getting file from the DB was failed for fileId: {}", fileId, e);
            return getInternalServerErrorResponse();
        }
    }

    /**
     * This method checks the validity of upload file name param
     *
     * @param uploadName uploadName
     * @return true/false
     */
    private boolean isValidUploadName(String uploadName) {
        return uploadName.matches("^[_A-z0-9]*((-)*[_A-z0-9])*$");
    }

    /**
     * This method verifies if the file types for upload is an allowed file type
     *
     * @param fileType file type
     * @return true/ false
     */
    private boolean isAllowedFileType(String fileType) {
        List<String> allowedFileTypes = new ArrayList<>();
        Arrays.asList(FileType.values())
                .forEach(ft -> allowedFileTypes.add(ft.getType()));
        return allowedFileTypes.contains(fileType);
    }
}
