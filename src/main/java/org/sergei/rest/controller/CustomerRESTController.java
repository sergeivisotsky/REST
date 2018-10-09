package org.sergei.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.sergei.rest.dto.CustomerDTO;
import org.sergei.rest.exceptions.RecordNotFoundException;
import org.sergei.rest.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/customers",
        produces = {"application/json", "application/xml"})
public class CustomerRESTController {

    @Autowired
    private CustomerService customerService;

    // Get all customers
    @GetMapping
    @ApiOperation(value = "Gel all customers")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    // Get customer by specific ID as a parameter
    @GetMapping("/{customerNumber}")
    @ApiOperation(value = "Get customer by ID")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("customerNumber") Long customerNumber) throws RecordNotFoundException {
        return new ResponseEntity<>(customerService.getCustomerByNumber(customerNumber), HttpStatus.OK);
    }

    // Add a new record
    @PostMapping(consumes = {"application/json", "application/xml"})
    @ApiOperation(value = "Add a new customer")
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.saveCustomer(customerDTO), HttpStatus.CREATED);
    }

    // Update record
    @PutMapping(value = "/{customerNumber}", consumes = {"application/json", "application/xml"})
    @ApiOperation(value = "Update customer data")
    public ResponseEntity<CustomerDTO> updateRecord(@PathVariable("customerNumber") Long customerNumber,
                                                    @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.updateCustomer(customerNumber, customerDTO), HttpStatus.ACCEPTED);
    }

    // Delete customer by specific number
    @DeleteMapping("/{customerNumber}")
    @ApiOperation(value = "Delete customer by number")
    public ResponseEntity<CustomerDTO> deleteCustomerByNumber(@PathVariable("customerNumber") Long customerNumber) {
        return new ResponseEntity<>(customerService.deleteCustomerByNumber(customerNumber), HttpStatus.OK);
    }
}
