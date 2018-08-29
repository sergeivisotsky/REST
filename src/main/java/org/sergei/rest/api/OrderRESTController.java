package org.sergei.rest.api;

import org.sergei.rest.persistence.pojo.Order;
import org.sergei.rest.persistence.service.repos.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1",
        produces = {"application/json", "application/xml"})
public class OrderRESTController {

    @Autowired
    private OrderService orderService;

    // Get all orders
    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Get order by specific ID as a parameter
    @GetMapping("/orders/{id}")
    public Order getOrderById(@PathVariable("id") Long id) {
        return orderService.getOrderById(id);
    }

    // Get order by customer id and order id
    @GetMapping("/customers/{customerId}/orders/{orderId}")
    public Order getOrderByCustomerIdAndOrderId(@PathVariable("customerId") Long customerId,
                                                @PathVariable("orderId") Long orderId) {
        return orderService.getOrderByCustomerIdAndOrderId(customerId, orderId);
    }

    // Get all orders by customer id
    @GetMapping("/customers/{id}/orders")
    public List<Order> getOrdersByCustomerId(@PathVariable("id") Long id) {
        return orderService.getAllOrdersByCustomerId(id);
    }

    // Get als orders by customer id and good
    @GetMapping("/customers/{customerId}/orders/order")
    public List<Order> getOrderByCustomerIdAndGood(@PathVariable("customerId") Long customerId,
                                                   @RequestParam("good") String good) {
        return orderService.getAllOrdersByCustomerIdAndGood(customerId, good);
    }

    // Get all orders by good name
    @GetMapping("/orders/order")
    public List<Order> getOrdersByGood(@RequestParam("good") String good) {
        return orderService.getAllByGood(good);
    }

    // Add a new record
    @PostMapping(value = "/customers/{id}/orders",
            consumes = {"application/json", "application/xml"})
    public ResponseEntity createOrder(@PathVariable("id") Long id,
                                      @RequestBody Order order) {
        orderService.saveOrder(id, order);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    // Update record
    @PutMapping(value = "/customers/{customerId}/orders/{orderId}", consumes = {"application/json", "application/xml"})
    public Order updateRecord(@PathVariable("customerId") Long customerId,
                              @PathVariable("orderId") Long orderId,
                              @RequestBody Order order) {
        return orderService.updateOrder(customerId, orderId, order);
    }

    // Delete order by specific ID
    @DeleteMapping("/orders/{id}")
    public ResponseEntity deleteOrderById(@PathVariable("id") Long id) {
        return orderService.deleteOrderById(id);
    }

    // Delete order by customer id and order id
    @DeleteMapping("/customers/{customerId}/orders/{orderId}")
    public ResponseEntity deleteOrderByCustomerIdAndOrderId(@PathVariable("customerId") Long customerId,
                                                            @PathVariable("orderId") Long orderId) {
        return orderService.deleteOrderByCustomerIdAndOrderId(customerId, orderId);
    }
}
