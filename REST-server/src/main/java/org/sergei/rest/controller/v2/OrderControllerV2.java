package org.sergei.rest.controller.v2;

import io.swagger.annotations.*;
import org.sergei.rest.dto.v2.OrderDTOV2;
import org.sergei.rest.service.v2.OrderServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
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
 * V2 of order controller
 */
@Api(
        value = "/api/v2/customers/{customerId}/orders",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class OrderControllerV2 {

    @Autowired
    private OrderServiceV2 orderServiceV2;

    @ApiOperation("Get all order by customer number")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @GetMapping("/v2/customers/{customerId}/orders")
    public ResponseEntity<Resources<OrderDTOV2>> getOrdersByCustomerIdV2(@ApiParam(value = "Customer ID whose orders should be found", required = true)
                                                                         @PathVariable("customerId") Long customerId) {
        List<OrderDTOV2> orderDTOV2List = orderServiceV2.findAllByCustomerIdV2(customerId);
        orderDTOV2List.forEach(orderDTOV2 -> {
            Link link = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(OrderControllerV2.class)
                            .getOrderByCustomerAndOrderIdV2(orderDTOV2.getCustomerId(), orderDTOV2.getOrderId())).withSelfRel();

            orderDTOV2.add(link);
        });
        Resources<OrderDTOV2> resources = new Resources<>(orderDTOV2List);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @ApiOperation("Get order by customer and order numbers")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer or order ID")
            }
    )
    @GetMapping("/v2/customers/{customerId}/orders/{orderId}")
    public ResponseEntity<OrderDTOV2> getOrderByCustomerAndOrderIdV2(@ApiParam(value = "Customer ID whose order should be found", required = true)
                                                                     @PathVariable("customerId") Long customerId,
                                                                     @ApiParam(value = "Order ID which should be found", required = true)
                                                                     @PathVariable("orderId") Long orderId) {
        OrderDTOV2 orderDTOV2 = orderServiceV2.findOneByCustomerIdAndOrderIdV2(customerId, orderId);
        Link link = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(OrderControllerV2.class)
                        .getOrderByCustomerAndOrderIdV2(orderDTOV2.getCustomerId(), orderDTOV2.getOrderId())).withSelfRel();
        Link allOrdersLink = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(OrderControllerV2.class)
                        .getOrdersByCustomerIdV2(orderDTOV2.getCustomerId())).withRel("allCustomerOrders");
        orderDTOV2.add(link);
        orderDTOV2.add(allOrdersLink);
        return new ResponseEntity<>(orderDTOV2, HttpStatus.OK);
    }

    @ApiOperation("Get all orders with specific product code")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid product code")
            }
    )
    @GetMapping("/v2/orders")
    public ResponseEntity<List<OrderDTOV2>> getOrdersByProductCodeV2(@ApiParam(value = "Code of the product which should be found", required = true)
                                                                     @RequestParam("prod-code") String productCode) {
        List<OrderDTOV2> orderDTOV2List = orderServiceV2.findAllByProductCodeV2(productCode);
        Resources<OrderDTOV2> resources = new Resources<>(orderDTOV2List);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return new ResponseEntity<>(orderDTOV2List, HttpStatus.OK);
    }
}
