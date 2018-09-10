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
@RequestMapping(value = "/api/v1/customers",
        produces = {"application/json", "application/xml"})
public class CustomerRESTController {

    @Autowired
    private CustomerService customerService;

    // Get all customers
    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // Get customer by specific ID as a parameter
    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("customerId") Long customerId) throws RecordNotFoundException {
        return customerService.getCustomerById(customerId);
    }

    // Add a new record
    @RequestMapping(method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"})
    public ResponseEntity addCustomer(@RequestBody Customer customer) {
        customerService.saveCustomer(customer);

        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    // Update record
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
            consumes = {"application/json", "application/xml"})
    public ResponseEntity<Customer> updateRecord(@PathVariable("id") Long id,
                                                 @RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.updateCustomer(id, customer), HttpStatus.ACCEPTED);
    }

    // Delete order by specific ID
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable("id") Long id) {
        Customer customer = customerService.deleteCustomerById(id);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}
