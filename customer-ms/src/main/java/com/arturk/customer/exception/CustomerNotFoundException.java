package com.arturk.customer.exception;

import com.arturk.common.exception.BusinessMarketAppException;

public class CustomerNotFoundException extends BusinessMarketAppException {

    public final static String code = "CUSTOMER_MS-02";

    public CustomerNotFoundException() {
        this(null);
    }

    public CustomerNotFoundException(String details) {
        super(code, "Customer not found", details);
    }
}
