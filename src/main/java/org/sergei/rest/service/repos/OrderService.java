package org.sergei.rest.service.repos;

import org.sergei.rest.model.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<Order> getAllOrders();

    Order getOrderById(Long id);

    Order getOrderByCustomerIdAndOrderId(Long customerId, Long orderId);

    List<Order> getAllOrdersByCustomerId(Long id);

    List<Order> getAllOrdersByCustomerIdAndGood(Long customerId, String good);

    List<Order> getAllByGood(String good);

    void saveOrder(Long customerId, Order order);

    Order updateOrder(Long customerId, Long orderId, Order order);

    ResponseEntity<Order> deleteOrderById(Long id);

    ResponseEntity<Order> deleteOrderByCustomerIdAndOrderId(Long customerId, Long orderId);
}
