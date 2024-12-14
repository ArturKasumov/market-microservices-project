package com.arturk.customer.convertor;

import com.arturk.customer.dto.CustomerDto;
import com.arturk.customer.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerConvertor {

    CustomerDto toCustomerDto(CustomerEntity source);
    CustomerEntity toCustomerEntity(CustomerDto source);
}

