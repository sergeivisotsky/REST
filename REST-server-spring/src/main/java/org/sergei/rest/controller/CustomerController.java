package org.sergei.rest.controller;

import io.swagger.annotations.*;
import org.sergei.rest.dto.CustomerDTO;
import org.sergei.rest.exceptions.RecordNotFoundException;
import org.sergei.rest.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Api(
        value = "/api/v1/customers",
        produces = "application/json, application/xml",
        consumes = "application/json, application/xml"
)
@RestController
@RequestMapping(value = "/api/v1/customers",
        produces = {"application/json", "application/xml"})
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @ApiOperation("Gel all customers")
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }

    @ApiOperation("Get customer by ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@ApiParam(value = "Customer ID which should be found", required = true)
                                                       @PathVariable("customerId") Long customerId) {
        return new ResponseEntity<>(customerService.findOne(customerId), HttpStatus.OK);
    }

    @ApiOperation("Add a new customer")
    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<CustomerDTO> saveCustomer(@ApiParam(value = "Saved customer", required = true)
                                                    @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.save(customerDTO), HttpStatus.CREATED);
    }

    @ApiOperation("Update customer data")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @PutMapping(value = "/{customerId}", consumes = {"application/json", "application/xml"})
    public ResponseEntity<CustomerDTO> updateRecord(@ApiParam(value = "Customer ID which should be updated", required = true)
                                                    @PathVariable("customerId") Long customerId,
                                                    @ApiParam(value = "Updated customer", required = true)
                                                    @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.update(customerId, customerDTO), HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Delete customer by number", notes = "Operation allowed for ADMIN only")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @DeleteMapping("/{customerId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomerDTO> deleteCustomerById(@ApiParam(value = "Customer ID which should be deleted", required = true)
                                                          @PathVariable("customerId") Long customerId) {
        return new ResponseEntity<>(customerService.deleteById(customerId), HttpStatus.NO_CONTENT);
    }
}