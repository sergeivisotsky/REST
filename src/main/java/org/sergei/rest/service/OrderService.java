package org.sergei.rest.service;

import org.sergei.rest.dao.OrderDAO;
import org.sergei.rest.exceptions.RecordNotFoundException;
import org.sergei.rest.model.Customer;
import org.sergei.rest.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDAO orderDAO;

    // Get all orders
    public List<Order> getAllOrders() {
        return orderDAO.findAll();
    }

    // Get order by number
    public List<Order> getOrderByNumber(Long orderNumber) {
        if (!orderDAO.existsByNumber(orderNumber)) {
            throw new RecordNotFoundException("No order with this ID found");
        }

        return orderDAO.findByNumber(orderNumber);
    }

    // Get order by customer and order numbers
    public List<Order> getOrderByCustomerAndOrderNumbers(Customer customerNumber, Long orderNumber) {
        if (!orderDAO.existsByCustomerNumber(customerNumber)) {
            throw new RecordNotFoundException("No customer with this number found");
        } else if (!orderDAO.existsByNumber(orderNumber)) {
            throw new RecordNotFoundException("No order with this number found");
        }

        return orderDAO.findByCustomerAndOrderNumbers(customerNumber, orderNumber);
    }

    // Get all orders by customer number
    public List<Order> getAllOrdersByCustomerNumber(Customer customerNumber) {
        if (!orderDAO.existsByCustomerNumber(customerNumber)) {
            throw new RecordNotFoundException("No orders for this customer found");
        }

        return orderDAO.findAllByCustomerNumber(customerNumber);
    }

    // Get all orders by product code
    public List<Order> getAllByProductCode(String productCode) {
        if (!orderDAO.existsByProductCode(productCode)) {
            throw new RecordNotFoundException("No order with this productCode code found");
        }

        return orderDAO.findAllByProductCode(productCode);
    }

    // Save order
    public void saveOrder(Customer customerNumber, Order order) {
        order.setCustomer(customerNumber);
        orderDAO.saveOrder(order);
    }

    // Update order by customer and order numbers
    public Order updateOrder(Customer customerNumber, Long orderNumber, Order order) {
        if (!orderDAO.existsByCustomerNumber(customerNumber)) {
            throw new RecordNotFoundException("No customer with this number found");
        } else if (!orderDAO.existsByNumber(orderNumber)) {
            throw new RecordNotFoundException("No order with this number found");
        }
        order.setOrderNumber(orderNumber);
        order.setCustomer(customerNumber);
        orderDAO.updateRecord(customerNumber, orderNumber, order);
        return order;
    }

    public List<Order> deleteOrderByNumber(Long orderNumber) {
        List<Order> order = orderDAO.findByNumber(orderNumber);
        if (!orderDAO.existsByNumber(orderNumber)) {
            throw new RecordNotFoundException("No order with this ID found");
        }

        orderDAO.deleteByOrderNumber(orderNumber);

        return order;
    }

    public List<Order> deleteOrderByCustomerIdAndOrderId(Customer customerNumber, Long orderNumber) {
        List<Order> order = orderDAO.findByCustomerAndOrderNumbers(customerNumber, orderNumber);
        if (!orderDAO.existsByCustomerNumber(customerNumber)) {
            throw new RecordNotFoundException("No customer with this ID found");
        } else if (!orderDAO.existsByNumber(orderNumber)) {
            throw new RecordNotFoundException("No order with this ID found");
        }


        orderDAO.deleteByCustomerAndOrderNumbers(customerNumber, orderNumber);

        return order;
    }
}
