package com.arturk.storage.exception;

import com.arturk.common.exception.BusinessMarketAppException;

public class ManufacturerNotFoundException extends BusinessMarketAppException {

    private final static String code = "STORAGE_MS-02";

    public ManufacturerNotFoundException() {
        this(null);
    }

    public ManufacturerNotFoundException(String details) {
        super(code, "Manufacturer not found", details);
    }
}
