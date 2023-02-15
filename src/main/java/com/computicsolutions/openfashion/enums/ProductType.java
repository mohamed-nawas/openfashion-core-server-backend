package com.computicsolutions.openfashion.enums;

import lombok.Getter;

/**
 * Enum values for product type
 */
@Getter
public enum ProductType {

    NEW_ARRIVALS("NEW_ARRIVALS"),
    WOMEN_WEAR("WOMEN_WEAR"),
    MEN_WEAR("MEN_WEAR"),
    KID_WEAR("KID_WEAR"),
    BAGS_SHOES("BAGS_SHOES"),
    HEALTH_BEAUTY("HEALTH_BEAUTY"),
    HOME_LIFESTYLE("HOME_LIFESTYLE"),
    MOTHER_BABY_CARE("MOTHER_BABY_CARE");

    private final String type;

    ProductType(String type) {
        this.type = type;
    }
}
