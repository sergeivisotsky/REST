/*
 * Copyright 2018-2019 Sergei Visotsky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sergei.rest.controller;

import io.swagger.annotations.*;
import org.sergei.rest.dto.CustomerDTO;
import org.sergei.rest.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Api(
        value = "/api/v1/customers",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation("Gel all customers")
    @GetMapping("/v1/customers")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }

    @ApiOperation("Get customer by ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @GetMapping("/v1/customers/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@ApiParam(value = "Customer ID which should be found", required = true)
                                                       @PathVariable("customerId") Long customerId) {
        return new ResponseEntity<>(customerService.findOne(customerId), HttpStatus.OK);
    }

    @ApiOperation("Add a new customer")
    @PostMapping(value = {
            "/v1/customers",
            "/v2/customers"
    }, consumes = "application/json")
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
    @PutMapping(value = {
            "/v1/customers/{customerId}",
            "/v2/customers/{customerId}"
    }, consumes = "application/json")
    public ResponseEntity<CustomerDTO> updateRecord(@ApiParam(value = "Customer ID which should be updated", required = true)
                                                    @PathVariable("customerId") Long customerId,
                                                    @ApiParam(value = "Updated customer", required = true)
                                                    @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.update(customerId, customerDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete customer by number", notes = "Operation allowed for ADMIN only")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @DeleteMapping({
            "/v1/customers/{customerId}",
            "/v2/customers/{customerId}"
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomerDTO> deleteCustomerById(@ApiParam(value = "Customer ID which should be deleted", required = true)
                                                          @PathVariable("customerId") Long customerId) {
        return new ResponseEntity<>(customerService.deleteById(customerId), HttpStatus.NO_CONTENT);
    }
}
