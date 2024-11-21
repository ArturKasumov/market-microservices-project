package com.arturk.storage.enums;

import lombok.Getter;

public enum ProductCategoryEnum {

    CHAIR ("Chair"),
    SOFA ("Sofa"),
    BED ("Bed"),
    TABLE ("Table"),
    WARDROBE ("Wardrobe"),
    DESK ("Desk"),
    SHELF ("Shelf");

    @Getter
    private final String category;

    ProductCategoryEnum(String category) {
        this.category = category;
    }

}
