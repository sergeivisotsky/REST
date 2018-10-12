package org.sergei.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.sergei.rest.dto.OrderDTO;
import org.sergei.rest.model.Order;
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
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    // Get order by specific ID as a parameter
    @GetMapping("/orders/{orderNumber}")
    @ApiOperation(value = "Get order by ID")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("orderNumber") Long orderNumber) {
        return new ResponseEntity<>(orderService.getOrderByNumber(orderNumber), HttpStatus.OK);
    }

    // Get all orders by customer orderNumber
    @GetMapping("/customers/{customerNumber}/orders")
    @ApiOperation(value = "Get all order by customer number")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomerNumber(@PathVariable("customerNumber") Long customerNumber) {
        return new ResponseEntity<>(orderService.getAllOrdersByCustomerNumber(customerNumber), HttpStatus.OK);
    }

    // Get order by customer orderNumber and order orderNumber
    @GetMapping("/customers/{customerNumber}/orders/{orderNumber}")
    @ApiOperation(value = "Get order by customer and order numbers")
    public ResponseEntity<OrderDTO> getOrderByCustomerAndOrderNumbers(@PathVariable("customerNumber") Long customerNumber,
                                                                      @PathVariable("orderNumber") Long orderNumber) {
        return new ResponseEntity<>(orderService.getOrderByCustomerAndOrderNumbers(customerNumber, orderNumber), HttpStatus.OK);
    }

    // Get all orders by product code
    @GetMapping("/orders/order")
    @ApiOperation(value = "Get order by product code")
    public List<OrderDTO> getOrdersByProductCode(@RequestParam("prod-code") String productCode) {
        return orderService.getAllByProductCode(productCode);
    }

    // Add a new record
    @PostMapping(value = "/customers/{customerNumber}/orders",
            consumes = {"application/json", "application/xml"})
    @ApiOperation(value = "Add a new order for the customer")
    public ResponseEntity<OrderDTO> createOrder(@PathVariable("customerNumber") Long customerNumber,
                                                @RequestBody OrderDTO orderDTO) {
        return new ResponseEntity<>(orderService.saveOrder(customerNumber, orderDTO), HttpStatus.CREATED);
    }

    // Update record
    @PutMapping(value = "/customers/{customerNumber}/orders/{orderNumber}",
            consumes = {"application/json", "application/xml"})
    @ApiOperation(value = "Update order by customer and order numbers")
    public ResponseEntity<OrderDTO> updateRecord(@PathVariable("customerNumber") Long customerNumber,
                                                 @PathVariable("orderNumber") Long orderNumber,
                                                 @RequestBody OrderDTO orderDTO) {
        return new ResponseEntity<>(orderService.updateOrder(customerNumber, orderNumber, orderDTO), HttpStatus.ACCEPTED);
    }

    // Delete order by number
    @DeleteMapping("/orders/{orderNumber}")
    @ApiOperation(value = "Delete order from all by number")
    public ResponseEntity<OrderDTO> deleteOrderByNumber(@PathVariable("orderNumber") Long orderNumber) {
        return new ResponseEntity<>(orderService.deleteOrderByNumber(orderNumber), HttpStatus.OK);
    }

    // Delete order by customer number and order number
    @DeleteMapping("/customers/{customerNumber}/orders/{orderNumber}")
    @ApiOperation(value = "Delete order by customer and order numbers")
    public ResponseEntity<OrderDTO> deleteOrderByCustomerNumberAndOrderNumber(@PathVariable("customerNumber") Long customerNumber,
                                                                              @PathVariable("orderNumber") Long orderNumber) {
        return new ResponseEntity<>(orderService.deleteOrderByCustomerIdAndOrderId(customerNumber, orderNumber), HttpStatus.OK);
    }
}
