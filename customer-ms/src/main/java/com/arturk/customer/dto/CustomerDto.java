package com.arturk.customer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto {

    private Long id;

    @NotBlank(message = "Name must be filled")
    private String name;

    @NotNull(message = "AmountOfMoney must be filled")
    private Integer amountOfMoney;
}
