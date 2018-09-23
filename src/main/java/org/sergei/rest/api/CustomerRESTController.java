package org.sergei.rest.api;

import org.sergei.rest.exceptions.RecordNotFoundException;
import org.sergei.rest.model.Customer;
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
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    // Get customer by specific ID as a parameter
    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") Long customerId) throws RecordNotFoundException {
        return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
    }

    // Add a new record
    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity addCustomer(@RequestBody Customer customer) {
        customerService.saveCustomer(customer);

        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    // Update record
    @PutMapping(value = "/{customerId}", consumes = {"application/json", "application/xml"})
    public ResponseEntity<Customer> updateRecord(@PathVariable("customerId") Long customerId,
                                                 @RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.updateCustomer(customerId, customer), HttpStatus.ACCEPTED);
    }

    // Delete order by specific ID
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable("customerId") Long customerId) {
        Customer customer = customerService.deleteCustomerById(customerId);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}
