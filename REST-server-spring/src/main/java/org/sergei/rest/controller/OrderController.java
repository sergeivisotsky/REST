package org.sergei.rest.controller;

import io.swagger.annotations.*;
import org.sergei.rest.dto.OrderDTO;
import org.sergei.rest.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Api(
        value = "/api/v1/customers/{customerId}/orders",
        description = "Order API methods",
        produces = "application/json, application/xml",
        consumes = "application/json, application/xml"
)
@RestController
@RequestMapping(value = "/api/v1",
        produces = {"application/json", "application/xml"})
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("Get all order by customer number")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomerId(@ApiParam(value = "Customer ID whose orders should be found", required = true)
                                                                @PathVariable("customerId") Long customerId) {
        return new ResponseEntity<>(orderService.findAllByCustomerId(customerId), HttpStatus.OK);
    }

    @ApiOperation("Get order by customer and order numbers")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer or order ID")
            }
    )
    @GetMapping("/customers/{customerId}/orders/{orderId}")
    public ResponseEntity<OrderDTO> getOrderByCustomerAndOrderId(@ApiParam(value = "Customer ID whose order should be found", required = true)
                                                                 @PathVariable("customerId") Long customerId,
                                                                 @ApiParam(value = "Order ID which should be found", required = true)
                                                                 @PathVariable("orderId") Long orderId) {
        return new ResponseEntity<>(orderService.findOneByCustomerIdAndOrderId(customerId, orderId), HttpStatus.OK);
    }

    @ApiOperation("Get all orders with specific product code")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid product code")
            }
    )
    @GetMapping("/orders")
    public List<OrderDTO> getOrdersByProductCode(@ApiParam(value = "Code of the product which should be found", required = true)
                                                 @RequestParam("prod-code") String productCode) {
        return orderService.findAllByProductCode(productCode);
    }

    @ApiOperation("Add a new order for the customer")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @PostMapping(value = "/customers/{customerId}/orders",
            consumes = {"application/json", "application/xml"})
    public ResponseEntity<OrderDTO> createOrder(@ApiParam(value = "Customer ID who created order", required = true)
                                                @PathVariable("customerId") Long customerId,
                                                @ApiParam(value = "Saved order", required = true)
                                                @RequestBody OrderDTO orderDTO) {
        return new ResponseEntity<>(orderService.saveByCustomerId(customerId, orderDTO), HttpStatus.CREATED);
    }

    @ApiOperation("Update order by customer and order numbers")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer or order ID")
            }
    )
    @PutMapping(value = "/customers/{customerId}/orders/{orderId}",
            consumes = {"application/json", "application/xml"})
    public ResponseEntity<OrderDTO> updateRecord(@ApiParam(value = "Customer ID whose order should be updated", required = true)
                                                 @PathVariable("customerId") Long customerId,
                                                 @ApiParam(value = "Order ID which should be updated", required = true)
                                                 @PathVariable("orderId") Long orderId,
                                                 @ApiParam(value = "Updated order", required = true)
                                                 @RequestBody OrderDTO orderDTO) {
        return new ResponseEntity<>(orderService.updateByCustomerId(customerId, orderId, orderDTO), HttpStatus.ACCEPTED);
    }

    @ApiOperation("Delete order by customer and order numbers")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer or order ID")
            }
    )
    @DeleteMapping("/customers/{customerId}/orders/{orderId}")
    public ResponseEntity<OrderDTO> deleteOrderByCustomerIdAndOrderId(@ApiParam(value = "Customer ID whose order should be deleted", required = true)
                                                                      @PathVariable("customerId") Long customerId,
                                                                      @ApiParam(value = "Order ID whose order should be deleted", required = true)
                                                                      @PathVariable("orderId") Long orderId) {
        return new ResponseEntity<>(orderService.deleteByCustomerIdAndOrderId(customerId, orderId), HttpStatus.NO_CONTENT);
    }
}
