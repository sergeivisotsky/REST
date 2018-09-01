package org.sergei.rest.api;

import org.sergei.rest.exceptions.RecordNotFoundException;
import org.sergei.rest.model.Customer;
import org.sergei.rest.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/customers",
        produces = {"application/json", "application/xml"})
public class CustomerRESTController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerService customerService;

    // Get all customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // Get customer by specific ID as a parameter
    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable("customerId") Long customerId) throws RecordNotFoundException {
        return customerService.getCustomerById(customerId);
    }

    // Add a new record
    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity addCustomer(@RequestBody Customer customer) {
        customerService.saveCustomer(customer);

        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    // Update record
    @PutMapping(value = "/{id}", consumes = {"application/json", "application/xml"})
    public Customer updateRecord(@PathVariable("id") Long id,
                                 @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    // Delete order by specific ID
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCustomerById(@PathVariable("id") Long id) {
        return customerService.deleteCustomerById(id);
    }
}
