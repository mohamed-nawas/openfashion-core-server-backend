package com.computicsolutions.openfashion.entity;

import com.computicsolutions.openfashion.dto.request.ProductRegistrationRequestDto;
import com.computicsolutions.openfashion.enums.ProductType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Product entity
 */
@Entity
@Table(name = "product")
@NoArgsConstructor
@Getter
public class Product {

    @Transient
    private static final String PRODUCT_ID_PREFIX = "pid-";

    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private int pricePerQty;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private File image;
    @ElementCollection
    private List<String> colors;
    @ElementCollection
    private List<String> sizes;
    @Column(name = "product_type")
    @Enumerated(EnumType.STRING)
    private ProductType type;
    @Column
    private Date created;
    @Column
    private Date updated;

    public Product(ProductRegistrationRequestDto dto) {
        this.id = PRODUCT_ID_PREFIX + UUID.randomUUID();
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.pricePerQty = dto.getPricePerQty();
        this.image = dto.getImage();
        this.colors = dto.getColors();
        this.sizes = dto.getSizes();
        this.type = dto.getType();
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
