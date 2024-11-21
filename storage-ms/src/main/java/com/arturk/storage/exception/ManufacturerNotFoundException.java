package com.arturk.storage.exception;

import com.arturk.common.exception.MarketAppException;

public class ManufacturerNotFoundException extends MarketAppException {

    private final static String code = "STORAGE_MS-02";

    public ManufacturerNotFoundException() {
        this(null);
    }

    public ManufacturerNotFoundException(String details) {
        super(code, "Manufacturer not found", details);
    }
}
