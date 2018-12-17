package org.sergei.rest.controller.v2;

import io.swagger.annotations.*;
import org.sergei.rest.controller.CustomerController;
import org.sergei.rest.controller.OrderController;
import org.sergei.rest.controller.PhotoController;
import org.sergei.rest.dto.v2.CustomerDTOV2;
import org.sergei.rest.service.v2.CustomerServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

/**
 * @author Sergei Visotsky
 * @since 12/9/2018
 * <p>
 * V2 of customer controller
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
    public ResponseEntity<Resources<CustomerDTOV2>> getAllCustomersV2() {
        List<CustomerDTOV2> customerDTOList = customerServiceV2.findAllV2();
        customerDTOList.forEach(customer -> {
            Link link = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(CustomerControllerV2.class)
                            .getCustomerByIdV2(customer.getCustomerId())).withSelfRel();
            Link ordersLink = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(OrderController.class)
                            .getOrdersByCustomerId(customer.getCustomerId())).withRel("orders");
            Link photoLink = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(PhotoController.class)
                            .findAllCustomerPhotos(customer.getCustomerId())).withRel("photos");
            customer.add(link);
            customer.add(ordersLink);
            customer.add(photoLink);
        });
        Resources<CustomerDTOV2> resources = new Resources<>(customerDTOList);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @ApiOperation("Gel all customers opaginated")
    @GetMapping(value = "/v2/customers", params = {"page", "size"})
    public ResponseEntity<Resources<CustomerDTOV2>> getAllCustomersPaginatedV2(@ApiParam(value = "Number of page", required = true)
                                                                               @RequestParam("page") int page,
                                                                               @ApiParam(value = "Number of elements per page", required = true)
                                                                               @RequestParam("size") int size) {
        Page<CustomerDTOV2> customerDTOList = customerServiceV2.findAllPaginatedV2(page, size);
        customerDTOList.forEach(customer -> {
            Link link = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(CustomerControllerV2.class)
                            .getCustomerByIdV2(customer.getCustomerId())).withSelfRel();
            Link ordersLink = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(OrderController.class)
                            .getOrdersByCustomerId(customer.getCustomerId())).withRel("orders");
            Link photoLink = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(PhotoController.class)
                            .findAllCustomerPhotos(customer.getCustomerId())).withRel("photos");
            customer.add(link);
            customer.add(ordersLink);
            customer.add(photoLink);
        });
        Resources<CustomerDTOV2> resources = new Resources<>(customerDTOList);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return new ResponseEntity<>(resources, HttpStatus.OK);
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
        Link link = ControllerLinkBuilder.linkTo(CustomerController.class).withSelfRel();
        Link ordersLink = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(OrderController.class)
                        .getOrdersByCustomerId(customerId)).withRel("orders");
        Link photoLink = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(PhotoController.class)
                        .findAllCustomerPhotos(customerDTOV2.getCustomerId())).withRel("photos");
        Link allCustomers = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(CustomerControllerV2.class)
                        .getAllCustomersV2()).withRel("allCustomers");
        customerDTOV2.add(link);
        customerDTOV2.add(ordersLink);
        customerDTOV2.add(photoLink);
        customerDTOV2.add(allCustomers);
        return new ResponseEntity<>(customerDTOV2, HttpStatus.OK);
    }
}
