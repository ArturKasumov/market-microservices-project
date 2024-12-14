package com.arturk.common.exception;

public class TechnicalMarketAppException extends MarketAppException {

    public TechnicalMarketAppException(String code, String description, String details) {
        super(code, description, details);
    }
}
