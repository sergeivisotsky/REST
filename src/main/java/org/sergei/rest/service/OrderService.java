package org.sergei.rest.service;

import org.sergei.rest.dao.OrderDAO;
import org.sergei.rest.exceptions.RecordNotFoundException;
import org.sergei.rest.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
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
            throw new RecordNotFoundException("No order with this ID found");
        }

        return orderDAO.findById(id);
    }

    public Order getOrderByCustomerIdAndOrderId(Long customerId, Long orderId) {
        if (!orderDAO.existsByCustomerId(customerId)) {
            throw new RecordNotFoundException("No customer with this ID found");
        } else if (!orderDAO.existsById(orderId)) {
            throw new RecordNotFoundException("No order with this ID found");
        }

        return orderDAO.findByCustomerIdAndOrderId(customerId, orderId);
    }

    public List<Order> getAllOrdersByCustomerId(Long id) {
        if (!orderDAO.existsByCustomerId(id)) {
            throw new RecordNotFoundException("No customer with this ID found");
        }

        return orderDAO.findAllByCustomerId(id);
    }

    public List<Order> getAllOrdersByCustomerIdAndProduct(Long customerId, String good) {
        if (!orderDAO.existsByCustomerId(customerId) || !orderDAO.existsByProduct(good)) {
            throw new RecordNotFoundException("No order or customer with this ID found");
        }

        return orderDAO.findAllByCustomerIdAndProduct(customerId, good);
    }

    public List<Order> getAllByGood(String good) {
        if (!orderDAO.existsByProduct(good)) {
            throw new RecordNotFoundException("No order with this good name");
        }

        return orderDAO.findAllByProduct(good);
    }

    public void saveOrder(Long customerId, Order order) {
        order.setCustomerId(customerId);
        orderDAO.save(customerId, order);
    }

    public Order updateOrder(Long customerId, Long orderId, Order order) {
        if (!orderDAO.existsByCustomerId(customerId)) {
            throw new RecordNotFoundException("No customer with this ID found");
        } else if (!orderDAO.existsById(orderId)) {
            throw new RecordNotFoundException("No order with this ID found");
        }
        order.setOrderId(orderId);
        order.setCustomerId(customerId);
        orderDAO.updateRecord(customerId, orderId, order);
        return order;
    }

    public Order deleteOrderById(Long id) {
        Order order = orderDAO.findById(id);
        if (!orderDAO.existsById(id)) {
            throw new RecordNotFoundException("No order with this ID found");
        }
        order.setOrderId(id);
        orderDAO.delete(order);

        return order;
    }

    public Order deleteOrderByCustomerIdAndOrderId(Long customerId, Long orderId) {
        Order order = orderDAO.findByCustomerIdAndOrderId(customerId, orderId);
        if (!orderDAO.existsByCustomerId(customerId)) {
            throw new RecordNotFoundException("No customer with this ID found");
        } else if (!orderDAO.existsById(orderId)) {
            throw new RecordNotFoundException("No order with this ID found");
        }

        order.setOrderId(orderId);
        order.setCustomerId(customerId);
        orderDAO.delete(order);

        return order;
    }
}
