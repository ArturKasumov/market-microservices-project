package com.arturk.customer.service;

import com.arturk.customer.convertor.CustomerConvertor;
import com.arturk.customer.dto.CustomerDto;
import com.arturk.customer.entity.CustomerEntity;
import com.arturk.customer.entity.repository.CustomerRepository;
import com.arturk.customer.exception.ProvidedIdException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerConvertor customerConvertor;

    public CustomerDto createCustomer(@Valid CustomerDto customerDto) {
        if (customerDto.getId() != null) {
            throw new ProvidedIdException();
        }
        CustomerEntity customerEntity = customerConvertor.toCustomerEntity(customerDto);
        customerEntity = customerRepository.save(customerEntity);
        return customerConvertor.toCustomerDto(customerEntity);
    }

    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerConvertor::toCustomerDto)
                .collect(Collectors.toList());
    }

    public void deleteCustomerById(Long customerId) {
        customerRepository.deleteById(customerId);
    }
}
