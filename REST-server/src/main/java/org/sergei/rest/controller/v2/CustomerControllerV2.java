package org.sergei.rest.controller.v2;

import io.swagger.annotations.*;
import org.sergei.rest.dto.v2.CustomerDTOV2;
import org.sergei.rest.service.v2.CustomerServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.sergei.rest.controller.util.LinkUtil.setLinksForAllCustomers;
import static org.sergei.rest.controller.util.LinkUtil.setLinksForCustomer;

/**
 * V2 of customer controller
 *
 * @author Sergei Visotsky
 * @since 2.0.2
 * Created on 12/9/2018
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

    @ApiOperation("Gel all customers")
    @GetMapping("/v2/customers")
    public ResponseEntity<Resources> getAllCustomersV2() {
        List<CustomerDTOV2> customerDTOList = customerServiceV2.findAllV2();
        return new ResponseEntity<>(setLinksForAllCustomers(customerDTOList), HttpStatus.OK);
    }

    @ApiOperation("Gel all customers opaginated")
    @GetMapping(value = "/v2/customers", params = {"page", "size"})
    public ResponseEntity<Resources> getAllCustomersPaginatedV2(@ApiParam(value = "Number of page", required = true)
                                                                @RequestParam("page") int page,
                                                                @ApiParam(value = "Number of elements per page", required = true)
                                                                @RequestParam("size") int size) {
        Page<CustomerDTOV2> customerDTOList = customerServiceV2.findAllPaginatedV2(page, size);
        return new ResponseEntity<>(setLinksForAllCustomers(customerDTOList), HttpStatus.OK);
    }

    @ApiOperation("Get customer by ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @GetMapping("/v2/customers/{customerId}")
    public ResponseEntity<CustomerDTOV2> getCustomerByIdV2(@ApiParam(value = "Customer ID which should be found", required = true)
                                                           @PathVariable("customerId") Long customerId) {
        CustomerDTOV2 customerDTOV2 = customerServiceV2.findOneV2(customerId);
        return new ResponseEntity<>(setLinksForCustomer(customerDTOV2, customerId), HttpStatus.OK);
    }
}
