package org.sergei.rest.service;

import org.sergei.rest.exceptions.RecordNotFoundException;
import org.sergei.rest.dao.OrderDAO;
import org.sergei.rest.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDAO orderDAO;

    public List<Order> getAllOrders() {
        return orderDAO.findAll();
    }

    public Order getOrderById(Long id) {
        if (!orderDAO.existsById(id)) {
            throw new RecordNotFoundException("No record with this parameters found");
        }

        return orderDAO.findById(id);
    }

    public Order getOrderByCustomerIdAndOrderId(Long customerId, Long orderId) {
        if (!orderDAO.existsByCustomerId(customerId) || !orderDAO.existsById(orderId)) {
            throw new RecordNotFoundException("No record with this parameters found");
        }

        return orderDAO.findByCustomerIdAndOrderId(customerId, orderId);
    }

    public List<Order> getAllOrdersByCustomerId(Long id) {
        if (!orderDAO.existsByCustomerId(id)) {
            throw new RecordNotFoundException("No record with this parameters found");
        }

        return orderDAO.findAllByCustomerId(id);
    }

    public List<Order> getAllOrdersByCustomerIdAndGood(Long customerId, String good) {
        if (!orderDAO.existsByCustomerId(customerId) || !orderDAO.existsByGood(good)) {
            throw new RecordNotFoundException("No record with this parameters found");
        }

        return orderDAO.findAllByCustomerIdAndGood(customerId, good);
    }

    public List<Order> getAllByGood(String good) {
        if (!orderDAO.existsByGood(good)) {
            throw new RecordNotFoundException("No record with this parameters found");
        }

        return orderDAO.findAllByGood(good);
    }

    public void saveOrder(Long customerId, Order order) {
        orderDAO.save(customerId, order);
    }

    public Order updateOrder(Long customerId, Long orderId, Order order) {
        if (!orderDAO.existsByCustomerId(customerId) || !orderDAO.existsById(orderId)) {
            throw new RecordNotFoundException("Record with this parameters not found");
        }
        orderDAO.updateRecord(customerId, orderId, order);
        return order;
    }

    public Order deleteOrderById(Long id) {
        Order order = orderDAO.findById(id);
        if (!orderDAO.existsById(id)) {
            throw new RecordNotFoundException("Record with this parameters not found");
        }
        orderDAO.delete(order);

        return order;
    }

    public Order deleteOrderByCustomerIdAndOrderId(Long customerId, Long orderId) {
        Order order = orderDAO.findByCustomerIdAndOrderId(customerId, orderId);
        if (!orderDAO.existsByCustomerId(customerId) || !orderDAO.existsById(orderId)) {
            throw new RecordNotFoundException("Record with this parameters not found");
        }
        orderDAO.delete(order);

        return order;
    }
}
