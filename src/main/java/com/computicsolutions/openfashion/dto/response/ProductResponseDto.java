package com.computicsolutions.openfashion.dto.response;

import com.computicsolutions.openfashion.entity.Product;
import com.computicsolutions.openfashion.enums.ProductType;
import lombok.Getter;

import java.util.List;

/**
 * Product DTO for response
 */
@Getter
public class ProductResponseDto implements ResponseDto {

    private final String productId;
    private final String name;
    private final String description;
    private final int pricePerQty;
    private final String imageId;
    private final List<String> colors;
    private final List<String> sizes;
    private final ProductType type;

    public ProductResponseDto(Product product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.pricePerQty = product.getPricePerQty();
        this.imageId = product.getImage().getId();
        this.colors = product.getColors();
        this.sizes = product.getSizes();
        this.type = product.getType();
    }

    @Override
    public String toLogJson() {
        return new ProductLogResponseDto(this).toLogJson();
    }

    @Getter
    private static class ProductLogResponseDto implements ResponseDto {

        private final String productId;
        private final String name;
        private final String description;

        public ProductLogResponseDto(ProductResponseDto responseDto) {
            this.productId = responseDto.getProductId();
            this.name = responseDto.getName();
            this.description = responseDto.getDescription();
        }

        @Override
        public String toLogJson() {
            return toJson();
        }
    }
}
