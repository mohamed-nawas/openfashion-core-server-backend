package com.computicsolutions.openfashion.dto.response;

import com.computicsolutions.openfashion.entity.File;
import lombok.Getter;

/**
 * FileUpload DTO for response
 */
@Getter
public class FileUploadResponseDto implements ResponseDto {

    private final String fileId;
    private final String name;
    private final String type;
    private final byte[] data;

    public FileUploadResponseDto(File file) {
        this.fileId = file.getId();
        this.name = file.getName();
        this.type = file.getType();
        this.data = file.getData();
    }

    @Override
    public String toLogJson() {
        return toJson();
    }
}
