/*
 * Copyright 2018-2019 the original author.
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

package org.sergei.rest.controller.v2;

import io.swagger.annotations.*;
import org.sergei.rest.dto.v2.CustomerDTOV2;
import org.sergei.rest.service.Constants;
import org.sergei.rest.service.v2.CustomerServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.sergei.rest.controller.hateoas.LinkUtil.setLinksForAllCustomers;
import static org.sergei.rest.controller.hateoas.LinkUtil.setLinksForCustomer;

/**
 * V2 of customer controller
 *
 * @author Sergei Visotsky
 */
@Api(
        value = "/api/v2/customers",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class CustomerControllerV2 {
    private final CustomerServiceV2 customerServiceV2;

    @Autowired
    public CustomerControllerV2(CustomerServiceV2 customerServiceV2) {
        this.customerServiceV2 = customerServiceV2;
    }

    @ApiOperation("Get all customers")
    @GetMapping("/v2/customers")
    public ResponseEntity<Resources> getAllCustomersV2() {
        List<CustomerDTOV2> customerDTOList = customerServiceV2.findAllV2();
        return new ResponseEntity<>(setLinksForAllCustomers(customerDTOList), HttpStatus.OK);
    }

    @ApiOperation("Gel all customers paginated")
    @GetMapping(value = "/v2/customers", params = {"page", "size"})
    public ResponseEntity<Resources> getAllCustomersPaginatedV2(@ApiParam("Number of page")
                                                                @RequestParam("page") int page,
                                                                @ApiParam("Number of elements per page")
                                                                @RequestParam("size") int size) {
        Page<CustomerDTOV2> customerDTOList = customerServiceV2.findAllPaginatedV2(page, size);
        return new ResponseEntity<>(setLinksForAllCustomers(customerDTOList), HttpStatus.OK);
    }

    @ApiOperation("Get customer by ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = Constants.CUSTOMER_NOT_FOUND)
            }
    )
    @GetMapping("/v2/customers/{customerId}")
    public ResponseEntity<CustomerDTOV2> getCustomerByIdV2(@ApiParam(value = "Customer ID which should be found", required = true)
                                                           @PathVariable("customerId") Long customerId) {
        CustomerDTOV2 customerDTOV2 = customerServiceV2.findOneV2(customerId);
        return new ResponseEntity<>(setLinksForCustomer(customerDTOV2, customerId), HttpStatus.OK);
    }

    @ApiOperation("Update one or many customer fields")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = Constants.CUSTOMER_NOT_FOUND)
            }
    )
    @PatchMapping("/v2/customers/{customerId}")
    public ResponseEntity<CustomerDTOV2> patchCustomer(@ApiParam(value = "Customer ID which should be patched", required = true)
                                                       @PathVariable("customerId") Long customerId,
                                                       @RequestBody Map<String, Object> params) {
        CustomerDTOV2 customerDTOV2 = customerServiceV2.patch(customerId, params);
        return new ResponseEntity<>(setLinksForCustomer(customerDTOV2, customerId), HttpStatus.OK);
    }
}
