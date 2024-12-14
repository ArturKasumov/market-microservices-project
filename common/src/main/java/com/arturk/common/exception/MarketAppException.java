package com.arturk.common.exception;

import lombok.Getter;

@Getter
public abstract class MarketAppException extends RuntimeException {
    private final String code;
    private final String description;
    private final String details;

    public MarketAppException(String code, String description, String details) {
        super(description);
        this.code = code;
        this.description = description;
        this.details = details;
    }

}
