package com.arturk.customer.exception;

import com.arturk.common.exception.BusinessMarketAppException;

public class MoneyNotAvailableException extends BusinessMarketAppException {

    public final static String code = "CUSTOMER_MS-03";

    public MoneyNotAvailableException() {
        this(null);
    }

    public MoneyNotAvailableException(String details) {
        super(code, "Not enough money for operation", details);
    }
}
