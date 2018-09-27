package org.sergei.rest.service;

import org.sergei.rest.exceptions.RecordNotFoundException;
import org.sergei.rest.model.Order;
import org.sergei.rest.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get order by number
    public Order getOrderByNumber(Long orderNumber) {
        return orderRepository.findById(orderNumber)
                .orElseThrow(() -> new RecordNotFoundException("Customer with this number not found"));
    }

    // Get order by customer and order numbers
    public List<Order> getOrderByCustomerAndOrderNumbers(Long customerNumber, Long orderNumber) {
        return orderRepository.findByCustomerAndOrderNumbers(customerNumber, orderNumber)
                .orElseThrow(() -> new RecordNotFoundException("No record with this parameters found"));
    }

    // Get all orders by customer number
    public List<Order> getAllOrdersByCustomerNumber(Long customerNumber) {
        return orderRepository.findAllByCustomerNumber(customerNumber)
                .orElseThrow(() -> new RecordNotFoundException("No record with this parameters found"));
    }

    // Get all orders by product code
    public List<Order> getAllByProductCode(String productCode) {
        return orderRepository.findAllByProductCode(productCode)
                .orElseThrow(() -> new RecordNotFoundException("No product with this code found"));
    }

    // Save order
    public void saveOrder(Long customerNumber, Order order) {
//        order.setCustomer(customerNumber);
        orderRepository.save(order);
    }

    // Update order by customer and order numbers
    public Order updateOrder(Long customerNumber, Long orderNumber, Order order) {
        order.setOrderNumber(orderNumber);
        orderRepository.save(order);
        return order;
    }

    public List<Order> deleteOrderByNumber(Long orderNumber) {
        List<Order> order = orderRepository.findByNumber(orderNumber);
        orderRepository.deleteByOrderNumber(orderNumber);

        return order;
    }

    public List<Order> deleteOrderByCustomerIdAndOrderId(Long customerNumber, Long orderNumber) {
        List<Order> order = orderRepository.findByCustomerAndOrderNumbers(customerNumber, orderNumber)
                .orElseThrow(() -> new RecordNotFoundException("No record with this parameters found"));
        orderRepository.deleteByCustomerAndOrderNumbers(customerNumber, orderNumber);

        return order;
    }
}
