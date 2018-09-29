package org.sergei.rest.service;

import org.sergei.rest.dto.CustomerDTO;
import org.sergei.rest.dto.OrderDTO;
import org.sergei.rest.dto.OrderDetailsDTO;
import org.sergei.rest.exceptions.RecordNotFoundException;
import org.sergei.rest.model.Customer;
import org.sergei.rest.model.Order;
import org.sergei.rest.model.OrderDetails;
import org.sergei.rest.repository.CustomerRepository;
import org.sergei.rest.repository.OrderDetailsRepository;
import org.sergei.rest.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final OrderDetailsRepository orderDetailsRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        CustomerRepository customerRepository,
                        OrderDetailsRepository orderDetailsRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    // Get all orders
    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> response = new LinkedList<>();
        List<Order> orders = orderRepository.findAll();

        for (Order order : orders) {
            OrderDTO orderDTO = new OrderDTO();

            orderDTO.setOrderNumber(order.getOrderNumber());
            orderDTO.setOrderDate(order.getOrderDate());
            orderDTO.setRequiredDate(order.getRequiredDate());
            orderDTO.setShippedDate(order.getShippedDate());
            orderDTO.setStatus(order.getStatus());

            List<OrderDetails> orderDetailsList =
                    orderDetailsRepository.findAllByOrderNumber(orderDTO.getOrderNumber());

            List<OrderDetailsDTO> orderDetailsDTOS = new ArrayList<>();
            for (OrderDetails orderDetails : orderDetailsList) {
                OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();

                orderDetailsDTO.setProductCode(orderDetails.getProduct().getProductCode());
                orderDetailsDTO.setPrice(orderDetails.getPrice());
                orderDetailsDTO.setQuantityOrdered(orderDetails.getQuantityOrdered());

                orderDetailsDTOS.add(orderDetailsDTO);
            }

            orderDTO.setOrderDetailsDTO(orderDetailsDTOS);
            response.add(orderDTO);
        }

        return response;
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
        return orderRepository.findAllByCustomerNumber(customerNumber);
    }

    // Get all orders by product code
    public List<Order> getAllByProductCode(String productCode) {
        return orderRepository.findAllByProductCode(productCode);
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
