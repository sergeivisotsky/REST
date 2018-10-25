package org.sergei.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.sergei.rest.dto.OrderDTO;
import org.sergei.rest.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1",
        produces = {"application/json", "application/xml"})
public class OrderRESTController {

    @Autowired
    private OrderService orderService;

    // Get all orders
    @GetMapping("/orders")
    @ApiOperation(value = "Get all existing orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
    }

    // Get order by specific ID as a parameter
    @GetMapping("/orders/{orderId}")
    @ApiOperation(value = "Get order by ID")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("orderId") Long orderId) {
        return new ResponseEntity<>(orderService.findOne(orderId), HttpStatus.OK);
    }

    // Get all orders by customer orderId
    @GetMapping("/customers/{customerId}/orders")
    @ApiOperation(value = "Get all order by customer number")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomerId(@PathVariable("customerId") Long customerId) {
        return new ResponseEntity<>(orderService.findAllByCustomerId(customerId), HttpStatus.OK);
    }

    // Get order by customer orderId and order orderId
    @GetMapping("/customers/{customerId}/orders/{orderId}")
    @ApiOperation(value = "Get order by customer and order numbers")
    public ResponseEntity<OrderDTO> getOrderByCustomerAndOrderId(@PathVariable("customerId") Long customerId,
                                                                 @PathVariable("orderId") Long orderId) {
        return new ResponseEntity<>(orderService.findOneByCustomerIdAndOrderId(customerId, orderId), HttpStatus.OK);
    }

    // Get all orders by product code
    @GetMapping("/orders/order")
    @ApiOperation(value = "Get order by product code")
    public List<OrderDTO> getOrdersByProductCode(@RequestParam("prod-code") String productCode) {
        return orderService.findAllByProductCode(productCode);
    }

    // Add a new record
    @PostMapping(value = "/customers/{customerId}/orders",
            consumes = {"application/json", "application/xml"})
    @ApiOperation(value = "Add a new order for the customer")
    public ResponseEntity<OrderDTO> createOrder(@PathVariable("customerId") Long customerId,
                                                @RequestBody OrderDTO orderDTO) {
        return new ResponseEntity<>(orderService.saveByCustomerId(customerId, orderDTO), HttpStatus.CREATED);
    }

    // Update record
    @PutMapping(value = "/customers/{customerId}/orders/{orderId}",
            consumes = {"application/json", "application/xml"})
    @ApiOperation(value = "Update order by customer and order numbers")
    public ResponseEntity<OrderDTO> updateRecord(@PathVariable("customerId") Long customerId,
                                                 @PathVariable("orderId") Long orderId,
                                                 @RequestBody OrderDTO orderDTO) {
        return new ResponseEntity<>(orderService.updateByCustomerId(customerId, orderId, orderDTO), HttpStatus.ACCEPTED);
    }

    // Delete order by number
    @DeleteMapping("/orders/{orderId}")
    @ApiOperation(value = "Delete order from all by number")
    public ResponseEntity<OrderDTO> deleteOrderById(@PathVariable("orderId") Long orderId) {
        return new ResponseEntity<>(orderService.deleteById(orderId), HttpStatus.OK);
    }

    // Delete order by customer number and order number
    @DeleteMapping("/customers/{customerId}/orders/{orderId}")
    @ApiOperation(value = "Delete order by customer and order numbers")
    public ResponseEntity<OrderDTO> deleteOrderByCustomerIdAndOrderId(@PathVariable("customerId") Long customerId,
                                                                      @PathVariable("orderId") Long orderId) {
        return new ResponseEntity<>(orderService.deleteByCustomerIdAndOrderId(customerId, orderId), HttpStatus.OK);
    }
}
