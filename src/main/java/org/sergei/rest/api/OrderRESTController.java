package org.sergei.rest.api;

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
    @GetMapping(value = "/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    // Get order by specific ID as a parameter
    @GetMapping("/orders/{orderNumber}")
    public ResponseEntity<List<Order>> getOrderById(@PathVariable("orderNumber") Long orderNumber) {
        return new ResponseEntity<>(orderService.getOrderByNumber(orderNumber), HttpStatus.OK);
    }

    // Get all orders by customer id
    @GetMapping("/customers/{customerNumber}/orders")
    public ResponseEntity<List<Order>> getOrdersByCustomerId(@PathVariable("customerNumber") Long customerNumber) {
        return new ResponseEntity<>(orderService.getAllOrdersByCustomerNumber(customerNumber), HttpStatus.OK);
    }

    // Get order by customer id and order id
    @GetMapping("/customers/{customerNumber}/orders/{orderNumber}")
    public ResponseEntity<List<Order>> getOrderByCustomerIdAndOrderId(@PathVariable("customerNumber") Long customerNumber,
                                                @PathVariable("orderNumber") Long orderNumber) {
        return new ResponseEntity<>(orderService.getOrderByCustomerAndOrderNumbers(customerNumber, orderNumber), HttpStatus.OK);
    }



    // Get all orders by product code
    @GetMapping(value = "/orders/order")
    public List<Order> getOrdersByProductCode(@RequestParam("prod-code") String productCode) {
        return orderService.getAllByProductCode(productCode);
    }

    // Add a new record
    @PostMapping(value = "/customers/{customerNumber}/orders",
            consumes = {"application/json", "application/xml"})
    public ResponseEntity<Order> createOrder(@PathVariable("customerNumber") Long customerNumber,
                                             @RequestBody Order order) {
        orderService.saveOrder(customerNumber, order);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    // Update record
    @PutMapping(value = "/customers/{customerNumber}/orders/{orderNumber}",
            consumes = {"application/json", "application/xml"})
    public ResponseEntity<Order> updateRecord(@PathVariable("customerNumber") Long customerNumber,
                                              @PathVariable("orderNumber") Long orderNumber,
                                              @RequestBody Order order) {
        return new ResponseEntity<>(orderService.updateOrder(customerNumber, orderNumber, order), HttpStatus.ACCEPTED);
    }

    /*// Delete order by specific ID
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Order> deleteOrderById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(orderService.deleteOrderById(id), HttpStatus.OK);
    }

    // Delete order by customer id and order id
    @DeleteMapping("/customers/{customerNumber}/orders/{orderNumber}")
    public ResponseEntity<Order> deleteOrderByCustomerIdAndOrderId(@PathVariable("customerNumber") Long customerNumber,
                                                                   @PathVariable("orderNumber") Long orderNumber) {
        Order order = orderService.deleteOrderByCustomerIdAndOrderId(customerNumber, orderNumber);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }*/
}
