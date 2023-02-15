package com.computicsolutions.openfashion.enums;

import lombok.Getter;

/**
 * Enum values for file type
 */
@Getter
public enum FileType {

    JPEG("image/jpeg"),
    PNG("image/png"),
    PLAIN_TEXT("text/plain"),
    HTML_TEXT("text/html"),
    CSV_TEXT("text/csv"),
    AUDIO_MP3("audio/mpeg"),
    VIDEO_MP4("video/mp4"),
    APPLICATION_PDF("application/pdf");

    private final String type;

    FileType(String type) {
        this.type = type;
    }
}
