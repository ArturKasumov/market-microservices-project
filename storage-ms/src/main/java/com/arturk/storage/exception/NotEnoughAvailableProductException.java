package com.arturk.storage.exception;

import com.arturk.common.exception.BusinessMarketAppException;

public class NotEnoughAvailableProductException extends BusinessMarketAppException {

    private final static String code = "STORAGE_MS-03";

    public NotEnoughAvailableProductException() {
        this(null);
    }

    public NotEnoughAvailableProductException(String details) {
        super(code, "Product not enough for order", details);
    }
}
