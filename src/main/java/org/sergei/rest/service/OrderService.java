package org.sergei.rest.service;

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
        List<Order> orders = orderRepository.findAll();
        return getOrdersByListWithParam(orders);
    }

    // Get order by number
    public OrderDTO getOrderByNumber(Long orderNumber) {
        Order order = orderRepository.findById(orderNumber)
                .orElseThrow(() -> new RecordNotFoundException("Order with this number not found"));
        OrderDTO response = new OrderDTO();

        response.setOrderNumber(order.getOrderNumber());
        response.setOrderDate(order.getOrderDate());
        response.setRequiredDate(order.getRequiredDate());
        response.setShippedDate(order.getShippedDate());
        response.setStatus(order.getStatus());

        List<OrderDetails> orderDetailsList =
                orderDetailsRepository.findAllByOrderNumber(response.getOrderNumber());

        List<OrderDetailsDTO> orderDetailsDTOS = new ArrayList<>();
        for (OrderDetails orderDetails : orderDetailsList) {
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();

            orderDetailsDTO.setProductCode(orderDetails.getProduct().getProductCode());
            orderDetailsDTO.setPrice(orderDetails.getPrice());
            orderDetailsDTO.setQuantityOrdered(orderDetails.getQuantityOrdered());

            orderDetailsDTOS.add(orderDetailsDTO);
        }

        response.setOrderDetailsDTO(orderDetailsDTOS);

        return response;
    }

    // Get order by customer and order numbers
    public OrderDTO getOrderByCustomerAndOrderNumbers(Long customerNumber, Long orderNumber) {
        if (!customerRepository.existsById(customerNumber)) {
            throw new RecordNotFoundException("No customer with this number found");
        }
        return getOrderByNumber(orderNumber);
    }

    // Get all orders by customer number
    public List<OrderDTO> getAllOrdersByCustomerNumber(Long customerNumber) {
        if (!customerRepository.existsById(customerNumber)) {
            throw new RecordNotFoundException("No customer with this number found");
        }
        List<Order> orders = orderRepository.findAllByCustomerNumber(customerNumber);

        return getOrdersByListWithParam(orders);
    }

    // Get all orders by product code
    public List<OrderDTO> getAllByProductCode(String productCode) {
        List<Order> orders = orderRepository.findAllByProductCode(productCode);
        return getOrdersByListWithParam(orders);
    }

    // Service class to get order by specific parameter
    private List<OrderDTO> getOrdersByListWithParam(List<Order> orders) {
        List<OrderDTO> response = new LinkedList<>();

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


    /*public Order saveOrder(Long customerNumber, Order order) {
        return customerRepository.findById(customerNumber).map(customer -> {
            order.setCustomer(customer);
            order.setOrderDetails(order.getOrderDetails());
            return orderRepository.save(order);
        }).orElseThrow(() -> new RecordNotFoundException("No customer with this number found"));
    }*/

    // Save order
    // TODO: So that order and its details be saved
    public OrderDTO saveOrder(Long customerNumber, OrderDTO orderDTORequestBody) {
        if (!customerRepository.existsById(customerNumber)) {
            throw new RecordNotFoundException("No customer with this number found");
        }

        Customer customer = customerRepository.findById(customerNumber)
                .orElseThrow(() -> new RecordNotFoundException("No customer with this number found"));
        Order order = new Order();

        order.setOrderNumber(orderDTORequestBody.getOrderNumber());
        order.setCustomer(customer);
        order.setOrderDate(orderDTORequestBody.getOrderDate());
        order.setRequiredDate(orderDTORequestBody.getRequiredDate());
        order.setShippedDate(orderDTORequestBody.getShippedDate());
        order.setStatus(orderDTORequestBody.getStatus());
//        order.setOrderDetails((List<OrderDetails>) orderDTORequestBody.getOrderDetailsDTO());

        orderRepository.save(order);

        return orderDTORequestBody;
    }

    // Update order by customer and order numbers
    // TODO: So that order and its details be updated
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
