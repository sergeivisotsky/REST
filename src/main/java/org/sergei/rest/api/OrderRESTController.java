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
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Get order by specific ID as a parameter
    @RequestMapping(value = "/orders/{orderId}", method = RequestMethod.GET)
    public Order getOrderById(@PathVariable("orderId") Long orderId) {
        return orderService.getOrderById(orderId);
    }

    // Get order by customer id and order id
    @RequestMapping(value = "/customers/{customerId}/orders/{orderId}", method = RequestMethod.GET)
    public Order getOrderByCustomerIdAndOrderId(@PathVariable("customerId") Long customerId,
                                                @PathVariable("orderId") Long orderId) {
        return orderService.getOrderByCustomerIdAndOrderId(customerId, orderId);
    }

    // Get all orders by customer id
    @RequestMapping(value = "/customers/{customerId}/orders", method = RequestMethod.GET)
    public List<Order> getOrdersByCustomerId(@PathVariable("customerId") Long customerId) {
        return orderService.getAllOrdersByCustomerId(customerId);
    }

    // Get als orders by customer id and good
    @RequestMapping(value = "/customers/{customerId}/orders/order", method = RequestMethod.GET)
    public List<Order> getOrderByCustomerIdAndGood(@PathVariable("customerId") Long customerId,
                                                   @RequestParam("good") String good) {
        return orderService.getAllOrdersByCustomerIdAndGood(customerId, good);
    }

    // Get all orders by good name
    @RequestMapping(value = "/orders/order", method = RequestMethod.GET)
    public List<Order> getOrdersByGood(@RequestParam("good") String good) {
        return orderService.getAllByGood(good);
    }

    // Add a new record
    @RequestMapping(value = "/customers/{customerId}/orders", method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"})
    public ResponseEntity<Order> createOrder(@PathVariable("customerId") Long customerId,
                                             @RequestBody Order order) {
        orderService.saveOrder(customerId, order);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    // Update record
    @RequestMapping(value = "/customers/{customerId}/orders/{orderId}",
            method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<Order> updateRecord(@PathVariable("customerId") Long customerId,
                                              @PathVariable("orderId") Long orderId,
                                              @RequestBody Order order) {
        return new ResponseEntity<>(orderService.updateOrder(customerId, orderId, order), HttpStatus.ACCEPTED);
    }

    // Delete order by specific ID
    @RequestMapping(value = "/orders/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Order> deleteOrderById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(orderService.deleteOrderById(id), HttpStatus.OK);
    }

    // Delete order by customer id and order id
    @RequestMapping(value = "/customers/{customerId}/orders/{orderId}", method = RequestMethod.DELETE)
    public ResponseEntity<Order> deleteOrderByCustomerIdAndOrderId(@PathVariable("customerId") Long customerId,
                                                                   @PathVariable("orderId") Long orderId) {
        Order order = orderService.deleteOrderByCustomerIdAndOrderId(customerId, orderId);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
