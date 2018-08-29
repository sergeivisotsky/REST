package org.sergei.rest.service.impls;

import org.sergei.rest.exceptions.RecordNotFoundException;
import org.sergei.rest.dao.repos.OrderDAO;
import org.sergei.rest.model.Order;
import org.sergei.rest.service.repos.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDAO orderDAO;

    @Override
    public List<Order> getAllOrders() {
        return orderDAO.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        if (!orderDAO.existsById(id)) {
            throw new RecordNotFoundException("No record with this parameters found");
        }

        return orderDAO.findById(id);
    }

    @Override
    public Order getOrderByCustomerIdAndOrderId(Long customerId, Long orderId) {
        if (!orderDAO.existsByCustomerId(customerId) || !orderDAO.existsById(orderId)) {
            throw new RecordNotFoundException("No record with this parameters found");
        }

        return orderDAO.findByCustomerIdAndOrderId(customerId, orderId);
    }

    @Override
    public List<Order> getAllOrdersByCustomerId(Long id) {
        if (!orderDAO.existsByCustomerId(id)) {
            throw new RecordNotFoundException("No record with this parameters found");
        }

        return orderDAO.findAllByCustomerId(id);
    }

    @Override
    public List<Order> getAllOrdersByCustomerIdAndGood(Long customerId, String good) {
        if (!orderDAO.existsByCustomerId(customerId) || !orderDAO.existsByGood(good)) {
            throw new RecordNotFoundException("No record with this parameters found");
        }

        return orderDAO.findAllByCustomerIdAndGood(customerId, good);
    }

    @Override
    public List<Order> getAllByGood(String good) {
        if (!orderDAO.existsByGood(good)) {
            throw new RecordNotFoundException("No record with this parameters found");
        }

        return orderDAO.findAllByGood(good);
    }

    @Override
    public void saveOrder(Long customerId, Order order) {
        orderDAO.save(customerId, order);
    }

    @Override
    public Order updateOrder(Long customerId, Long orderId, Order order) {
        if (!orderDAO.existsByCustomerId(customerId) || !orderDAO.existsById(orderId)) {
            throw new RecordNotFoundException("Record with this parameters not found");
        }
        orderDAO.updateRecord(customerId, orderId, order);
        return order;
    }

    @Override
    public ResponseEntity<Order> deleteOrderById(Long id) {
        Order order = orderDAO.findById(id);
        if (!orderDAO.existsById(id)) {
            throw new RecordNotFoundException("Record with this parameters not found");
        }
        orderDAO.delete(order);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Order> deleteOrderByCustomerIdAndOrderId(Long customerId, Long orderId) {

        Order order = orderDAO.findByCustomerIdAndOrderId(customerId, orderId);

        if (!orderDAO.existsByCustomerId(customerId) || !orderDAO.existsById(orderId)) {
            throw new RecordNotFoundException("Record with this parameters not found");
        }
        orderDAO.delete(order);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
