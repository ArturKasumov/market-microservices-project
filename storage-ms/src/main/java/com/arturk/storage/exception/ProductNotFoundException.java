package com.arturk.storage.exception;

import com.arturk.common.exception.MarketAppException;

public class ProductNotFoundException extends MarketAppException {

    private final static String code = "STORAGE_MS-01";

    public ProductNotFoundException() {
        this(null);
    }

    public ProductNotFoundException(String details) {
        super(code, "Product not found", details);
    }
}
