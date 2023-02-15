package com.computicsolutions.openfashion.dto.response;

import com.computicsolutions.openfashion.entity.Product;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Product List DTO for response
 */
@Getter
public class ProductListResponseDto extends PageResponseDto {

    private final List<ProductResponseDto> products;

    public ProductListResponseDto(Page<Product> productPage) {
        super(productPage);
        this.products = new ArrayList<>();
        for (int i = 0; i < productPage.getContent().size(); i++) {
            Product product = productPage.getContent().get(i);
            products.add(new ProductResponseDto(product));
        }
    }
}
