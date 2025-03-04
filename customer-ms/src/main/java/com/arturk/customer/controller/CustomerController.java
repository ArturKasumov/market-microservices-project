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

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto customer = customerService.createCustomer(customerDto);
        return ResponseEntity.ok(customer);
    }

    @RequestMapping(
            value = "/{customerId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("customerId") Long customerId) {
        CustomerDto customer = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(customer);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @RequestMapping(
            value = "/{customerId}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomerById(customerId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(
            value = "/{customerId}/credit",
            method = RequestMethod.POST
    )
    public ResponseEntity<Void> creditCustomer(@PathVariable Long customerId, @RequestBody Double amount) {
        customerService.creditCustomer(customerId, amount);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(
            value = "/{customerId}/debit",
            method = RequestMethod.POST
    )
    public ResponseEntity<Void> debitCustomer(@PathVariable Long customerId, @RequestBody Double amount) {
        customerService.debitCustomer(customerId, amount);
        return ResponseEntity.ok().build();
    }
}
