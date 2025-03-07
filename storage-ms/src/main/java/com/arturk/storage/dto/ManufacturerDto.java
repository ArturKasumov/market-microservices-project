package com.arturk.storage.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ManufacturerDto implements Serializable {

    private Long id;
    private String name;
}
