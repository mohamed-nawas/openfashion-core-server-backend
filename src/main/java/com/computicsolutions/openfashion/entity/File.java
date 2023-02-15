package com.computicsolutions.openfashion.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * File entity
 */
@Entity
@Table(name = "file")
@NoArgsConstructor
@Getter
public class File {

    @Transient
    private static final String FILE_ID_PREFIX = "fid-";

    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    @Lob
    private byte[] data;
    @OneToOne(mappedBy = "image")
    private Product product;
    @Column
    private Date created;
    @Column
    private Date updated;

    public File(String name, String type, byte[] data) {
        this.id = FILE_ID_PREFIX + UUID.randomUUID();
        this.name = name;
        this.type = type;
        this.data = data;
    }

    @PrePersist
    private void onCreate() {
        created = new Date();
        updated = new Date();
    }

    @PreUpdate
    private void onUpdate() {
        updated = new Date();
    }
}
