package com.arturk.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestException {

    private String code;
    private String description;
    private String details;

    public RestException(String description) {
        this.description = description;
    }

    public RestException(String code, String description, String details) {
        this.code = code;
        this.description = description;
        this.details = details;
    }
}
