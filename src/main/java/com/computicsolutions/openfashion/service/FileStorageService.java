package com.computicsolutions.openfashion.service;

import com.computicsolutions.openfashion.entity.File;
import com.computicsolutions.openfashion.exception.OpenFashionCoreException;
import com.computicsolutions.openfashion.exception.UploadFileNotFoundException;
import com.computicsolutions.openfashion.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

/**
 * File Storage Service
 */
@Service
public class FileStorageService {

    private static final String IMAGE_NAME_PREFIX = "iid-";
    private final FileRepository fileRepository;

    @Autowired
    public FileStorageService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /**
     * This method generates unique image name.
     *
     * @return unique image name
     */
    private static String generateUniqueName() {
        return IMAGE_NAME_PREFIX + UUID.randomUUID();
    }

    /**
     * This method uploads the file to DB
     *
     * @param file     multi part file object
     * @param fileName file name to store in DB
     * @return upload url
     */
    public File uploadFile(MultipartFile file, String fileName) {
        String imageName = (fileName != null && !fileName.trim().isEmpty()) ? fileName : generateUniqueName();
        try {
            File fileDB = new File(imageName, file.getContentType(), file.getBytes());
            return fileRepository.save(fileDB);
        } catch (IOException | DataAccessException e) {
            throw new OpenFashionCoreException("Failed to upload file to DB", e);
        }
    }

    /**
     * This method finds a file by id in DB
     *
     * @param id file id
     * @return File/ null
     */
    public File getFileById(String id) {
        try {
            Optional<File> optionalFile = fileRepository.findById(id);
            if (!optionalFile.isPresent())
                throw new UploadFileNotFoundException("Upload file is not found");
            return optionalFile.get();
        } catch (DataAccessException e) {
            throw new OpenFashionCoreException("Failed to get file from DB for id: " + id, e);
        }
    }
}
