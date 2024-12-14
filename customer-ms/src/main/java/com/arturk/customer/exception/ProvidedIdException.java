package com.arturk.customer.exception;

import com.arturk.common.exception.BusinessMarketAppException;

public class ProvidedIdException extends BusinessMarketAppException {

    public final static String code = "CUSTOMER_MS-01";

    public ProvidedIdException() {
        this(null);
    }

    public ProvidedIdException(String details) {
        super(code, "Id must be null", details);
    }
}
