package com.arturk.customer.convertor;

import com.arturk.customer.dto.CustomerDto;
import com.arturk.customer.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface CustomerConvertor {

    @Mappings({
            @Mapping(target = "amountOfMoney", source = "amountOfMoney", qualifiedByName = "fromBigDecimal")
    })
    CustomerDto toCustomerDto(CustomerEntity source);

    @Mappings({
            @Mapping(target = "amountOfMoney", source = "amountOfMoney", qualifiedByName = "toBigDecimal")
    })
    CustomerEntity toCustomerEntity(CustomerDto source);

    @Named("fromBigDecimal")
    default Double fromBigDecimal(BigDecimal amountOfMoney) {
        return amountOfMoney.doubleValue();
    }

    @Named("toBigDecimal")
    default BigDecimal toBigDecimal(Double amountOfMoney) {
        return BigDecimal.valueOf(amountOfMoney);
    }
}

