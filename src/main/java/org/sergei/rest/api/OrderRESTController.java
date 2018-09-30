package org.sergei.rest.api;

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
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    // Get order by specific ID as a parameter
    @GetMapping("/orders/{orderNumber}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("orderNumber") Long orderNumber) {
        return new ResponseEntity<>(orderService.getOrderByNumber(orderNumber), HttpStatus.OK);
    }

    // Get all orders by customer orderNumber
    @GetMapping("/customers/{customerNumber}/orders")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomerId(@PathVariable("customerNumber") Long customerNumber) {
        return new ResponseEntity<>(orderService.getAllOrdersByCustomerNumber(customerNumber), HttpStatus.OK);
    }

    // Get order by customer orderNumber and order orderNumber
    @GetMapping("/customers/{customerNumber}/orders/{orderNumber}")
    public ResponseEntity<OrderDTO> getOrderByCustomerIdAndOrderId(@PathVariable("customerNumber") Long customerNumber,
                                                                   @PathVariable("orderNumber") Long orderNumber) {
        return new ResponseEntity<>(orderService.getOrderByCustomerAndOrderNumbers(customerNumber, orderNumber), HttpStatus.OK);
    }

    // Get all orders by product code
    @GetMapping("/orders/order")
    public List<OrderDTO> getOrdersByProductCode(@RequestParam("prod-code") String productCode) {
        return orderService.getAllByProductCode(productCode);
    }

    // Add a new record
    @PostMapping(value = "/customers/{customerNumber}/orders",
            consumes = {"application/json", "application/xml"})
    public ResponseEntity<OrderDTO> createOrder(@PathVariable("customerNumber") Long customerNumber,
                                                @RequestBody OrderDTO orderDTO) {
        return new ResponseEntity<>(orderService.saveOrder(customerNumber, orderDTO), HttpStatus.CREATED);
    }

    // Update record
    @PutMapping(value = "/customers/{customerNumber}/orders/{orderNumber}",
            consumes = {"application/json", "application/xml"})
    public ResponseEntity<Order> updateRecord(@PathVariable("customerNumber") Long customerNumber,
                                              @PathVariable("orderNumber") Long orderNumber,
                                              @RequestBody Order order) {
        return new ResponseEntity<>(orderService.updateOrder(customerNumber, orderNumber, order), HttpStatus.ACCEPTED);
    }

    // Delete order by number
    @DeleteMapping("/orders/{orderNumber}")
    public ResponseEntity<Order> deleteOrderByNumber(@PathVariable("orderNumber") Long orderNumber) {
        return new ResponseEntity<>(orderService.deleteOrderByNumber(orderNumber), HttpStatus.OK);
    }

    // Delete order by customer number and order number
    @DeleteMapping("/customers/{customerNumber}/orders/{orderNumber}")
    public ResponseEntity<Order> deleteOrderByCustomerNumberAndOrderNumber(@PathVariable("customerNumber") Long customerNumber,
                                                                           @PathVariable("orderNumber") Long orderNumber) {

        return new ResponseEntity<>(orderService.deleteOrderByCustomerIdAndOrderId(customerNumber, orderNumber), HttpStatus.OK);
    }
}
