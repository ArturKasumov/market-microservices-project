package com.arturk.customer.controller;

import com.arturk.customer.dto.CustomerDto;
import com.arturk.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v4/customer")
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto customer = customerService.createCustomer(customerDto);
        return ResponseEntity.ok(customer);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> getAllCustomers(@PathVariable Long customerId) {
        customerService.deleteCustomerById(customerId);
        return ResponseEntity.ok().build();
    }
}