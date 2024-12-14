package com.arturk.storage.exception;

import com.arturk.common.exception.BusinessMarketAppException;

public class ProductNotFoundException extends BusinessMarketAppException {

    private final static String code = "STORAGE_MS-01";

    public ProductNotFoundException() {
        this(null);
    }

    public ProductNotFoundException(String details) {
        super(code, "Product not found", details);
    }
}
