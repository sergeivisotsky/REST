package org.sergei.rest.api;

import org.sergei.rest.model.Order;
import org.sergei.rest.service.OrderService;
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
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Get order by specific ID as a parameter
    @RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
    public Order getOrderById(@PathVariable("id") Long id) {
        return orderService.getOrderById(id);
    }

    // Get order by customer id and order id
    @RequestMapping(value = "/customers/{customerId}/orders/{orderId}", method = RequestMethod.GET)
    public Order getOrderByCustomerIdAndOrderId(@PathVariable("customerId") Long customerId,
                                                @PathVariable("orderId") Long orderId) {
        return orderService.getOrderByCustomerIdAndOrderId(customerId, orderId);
    }

    // Get all orders by customer id
    @RequestMapping(value = "/customers/{id}/orders", method = RequestMethod.GET)
    public List<Order> getOrdersByCustomerId(@PathVariable("id") Long id) {
        return orderService.getAllOrdersByCustomerId(id);
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
    @RequestMapping(value = "/customers/{id}/orders", method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"})
    public ResponseEntity createOrder(@PathVariable("id") Long id,
                                      @RequestBody Order order) {
        orderService.saveOrder(id, order);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    // Update record
    @RequestMapping(value = "/customers/{customerId}/orders/{orderId}",
            method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public Order updateRecord(@PathVariable("customerId") Long customerId,
                              @PathVariable("orderId") Long orderId,
                              @RequestBody Order order) {
        return orderService.updateOrder(customerId, orderId, order);
    }

    // Delete order by specific ID
    @RequestMapping(value = "/orders/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOrderById(@PathVariable("id") Long id) {
        return orderService.deleteOrderById(id);
    }

    // Delete order by customer id and order id
    @RequestMapping(value = "/customers/{customerId}/orders/{orderId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOrderByCustomerIdAndOrderId(@PathVariable("customerId") Long customerId,
                                                            @PathVariable("orderId") Long orderId) {
        return orderService.deleteOrderByCustomerIdAndOrderId(customerId, orderId);
    }
}
