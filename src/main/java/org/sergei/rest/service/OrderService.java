package org.sergei.rest.service;

import org.sergei.rest.exceptions.RecordNotFoundException;
import org.sergei.rest.model.Order;
import org.sergei.rest.repository.CustomerRepository;
import org.sergei.rest.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

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
    public Order getOrderByCustomerAndOrderNumbers(Long customerNumber, Long orderNumber) {
        if (!customerRepository.existsById(customerNumber)) {
            throw new RecordNotFoundException("No customer with this number found");
        }
        return orderRepository.findById(orderNumber)
                .orElseThrow(() -> new RecordNotFoundException("No record with this parameters found"));
    }

    // Get all orders by customer number
    public List<Order> getAllOrdersByCustomerNumber(Long customerNumber) {
        if (!customerRepository.existsById(customerNumber)) {
            throw new RecordNotFoundException("No customer with this number found");
        }
        return orderRepository.findAllByCustomerNumber(customerNumber)
                .orElseThrow(() -> new RecordNotFoundException("No record with this parameters found"));
    }

    // Get all orders by product code
    public List<Order> getAllByProductCode(String productCode) {
        return orderRepository.findAllByProductCode(productCode)
                .orElseThrow(() -> new RecordNotFoundException("No product with this code found"));
    }

    // Save order
    public Order saveOrder(Long customerNumber, Order order) {
        return customerRepository.findById(customerNumber).map(customer -> {
            order.setCustomer(customer);
            return orderRepository.save(order);
        }).orElseThrow(() -> new RecordNotFoundException("No customer with this number found"));
    }

    // Update order by customer and order numbers
    public Order updateOrder(Long customerNumber, Long orderNumber, Order orderRequest) {
        if (!customerRepository.existsById(customerNumber)) {
            throw new RecordNotFoundException("No customer with this number found");
        }

        return orderRepository.findById(orderNumber).map(order -> {
            order.setOrderNumber(orderRequest.getOrderNumber());
            order.setOrderDate(orderRequest.getOrderDate());
            order.setRequiredDate(orderRequest.getRequiredDate());
            order.setShippedDate(orderRequest.getShippedDate());
            order.setStatus(orderRequest.getStatus());
            return orderRepository.save(order);
        }).orElseThrow(() -> new RecordNotFoundException("Order with this number not found"));
    }

    public Order deleteOrderByNumber(Long orderNumber) {
        return orderRepository.findById(orderNumber).map(order -> {
            orderRepository.delete(order);
            return order;
        }).orElseThrow(() -> new RecordNotFoundException("Order with this number not found"));
    }

    public Order deleteOrderByCustomerIdAndOrderId(Long customerNumber, Long orderNumber) {
        if (!customerRepository.existsById(customerNumber)) {
            throw new RecordNotFoundException("No customer with this number found");
        }

        return orderRepository.findById(orderNumber).map(order -> {
            orderRepository.delete(order);
            return order;
        }).orElseThrow(() -> new RecordNotFoundException("Order with this number not found"));
    }
}
