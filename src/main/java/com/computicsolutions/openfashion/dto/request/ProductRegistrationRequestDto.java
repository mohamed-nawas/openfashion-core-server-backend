package com.computicsolutions.openfashion.dto.request;

import com.computicsolutions.openfashion.entity.File;
import com.computicsolutions.openfashion.enums.ProductType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO for Product registration request
 */
@Getter
@Setter
public class ProductRegistrationRequestDto implements RequestDto {

    private String name;
    private String description;
    private int pricePerQty;
    private File image;
    private List<String> colors;
    private List<String> sizes;
    private ProductType type;

    @Override
    public String toLogJson() {
        return toJson();
    }

    @Override
    public boolean isRequiredAvailable() {
        return isNonEmpty(name) && isNonEmpty(description) && pricePerQty > 0 && image != null
                && colors.size() > 0 && sizes.size() > 0 && isNonEmpty(type.name());
    }

    @Override
    public boolean isNonEmpty(String field) {
        return field != null && !field.trim().isEmpty();
    }
}
