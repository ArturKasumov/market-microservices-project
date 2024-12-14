package com.arturk.common.exception;

public class BusinessMarketAppException extends MarketAppException{

    public BusinessMarketAppException(String code, String description, String details) {
        super(code, description, details);
    }
}
