/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.rest.service;

import org.modelmapper.ModelMapper;
import org.sergei.rest.dao.CustomerDAO;
import org.sergei.rest.dao.OrderDAO;
import org.sergei.rest.dao.OrderDetailsDAO;
import org.sergei.rest.dao.ProductDAO;
import org.sergei.rest.dto.OrderDTO;
import org.sergei.rest.dto.OrderDetailsDTO;
import org.sergei.rest.exceptions.RecordNotFoundException;
import org.sergei.rest.exceptions.ResourceNotFoundException;
import org.sergei.rest.model.Customer;
import org.sergei.rest.model.Order;
import org.sergei.rest.model.OrderDetails;
import org.sergei.rest.model.Product;
import org.sergei.rest.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class OrderService {

    private final ModelMapper modelMapper;
    private final OrderDAO orderDAO;
    private final OrderDetailsDAO orderDetailsDAO;
    private final CustomerDAO customerDAO;
    private final ProductDAO productDAO;

    @Autowired
    public OrderService(ModelMapper modelMapper, OrderDAO orderDAO, OrderDetailsDAO orderDetailsDAO,
                        CustomerDAO customerDAO, ProductDAO productDAO) {
        this.modelMapper = modelMapper;
        this.orderDAO = orderDAO;
        this.orderDetailsDAO = orderDetailsDAO;
        this.customerDAO = customerDAO;
        this.productDAO = productDAO;
    }

    /**
     * Get all orders
     *
     * @return List of order DTOs
     */
    public List<OrderDTO> findAll() {
        List<Order> orders = orderDAO.findAll();
        return findOrdersByListWithParam(orders);
    }

    /**
     * Get order by number
     *
     * @param orderId get order number as a parameter from REST controller
     * @return order DTO response
     */
    public OrderDTO findOne(Long orderId) {
        Order order = orderDAO.findOne(orderId);
        if (order == null) {
            throw new ResourceNotFoundException("Order with this ID not found");
        }
        // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

        List<OrderDetails> orderDetailsList =
                orderDetailsDAO.findAllByOrderId(orderDTO.getOrderId());

        List<OrderDetailsDTO> orderDetailsDTOList = new ArrayList<>();
        orderDetailsList.forEach(orderDetails ->
                orderDetailsDTOList.add(
                        modelMapper.map(orderDetails, OrderDetailsDTO.class)
                )
        );

        orderDTO.setOrderDetailsDTO(orderDetailsDTOList);

        return orderDTO;
    }

    /**
     * Get order by customer and order numbers
     *
     * @param customerId Get customer number from the REST controller
     * @param orderId    Get order number from the REST controller
     * @return Return order DTO reponse
     */
    public OrderDTO findOneByCustomerIdAndOrderId(Long customerId, Long orderId) {
        if (customerDAO.findOne(customerId) == null) {
            throw new RecordNotFoundException("No customer with this number found");
        }
        return findOne(orderId);
    }

    /**
     * Get all orders by customer number
     *
     * @param customerId customer number form the REST controller
     * @return List of order DTOs
     */
    public List<OrderDTO> findAllByCustomerId(Long customerId) {
        List<Order> orders = orderDAO.findAllByCustomerId(customerId);
        if (orders == null) {
            throw new ResourceNotFoundException("No orders with this customer ID found");
        }

        return findOrdersByListWithParam(orders);
    }

    /**
     * Get all orders by product code
     *
     * @param productCode get product code from the REST controller
     * @return return list of order DTOs
     */
    public List<OrderDTO> findAllByProductCode(String productCode) {
        List<Order> orders = orderDAO.findAllByProductCode(productCode);
        if (orders == null) {
            throw new ResourceNotFoundException("Orders with this product code not found");
        }
        return findOrdersByListWithParam(orders);
    }

    /**
     * Save order
     *
     * @param customerId Get customer number from the REST controller
     * @param orderDTO   Get order DTO request body
     * @return return order DTO as a response
     */
    public OrderDTO saveByCustomerId(Long customerId, OrderDTO orderDTO) {
        Customer customer = customerDAO.findOne(customerId);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer with this ID not found");
        }

        Order order = modelMapper.map(orderDTO, Order.class);
        order.setCustomer(customer);

        // Maps each member of collection containing requests to the class
        List<OrderDetails> orderDetailsList = ObjectMapperUtils
                .mapAll(orderDTO.getOrderDetailsDTO(), OrderDetails.class);

        int counter = 0;
        for (OrderDetails orderDetails : orderDetailsList) {
            Product product = productDAO.findByCode(orderDTO.getOrderDetailsDTO().get(counter).getProductCode());
            orderDetails.setOrder(order);
            orderDetails.setProduct(product);
            orderDetails.setQuantityOrdered(orderDTO.getOrderDetailsDTO().get(counter).getQuantityOrdered());
            orderDetails.setPrice(orderDTO.getOrderDetailsDTO().get(counter).getPrice());
            counter++;
        }

        order.setOrderDetails(orderDetailsList);

        orderDAO.save(order);

        return orderDTO;
    }

    /**
     * Update order by customer and order numbers
     *
     * @param customerId get customer number form the REST controller
     * @param orderId    get order number form the REST controller
     * @param orderDTO   Get order DTO request body
     * @return return order DTO as a response
     */
    public OrderDTO updateByCustomerId(Long customerId, Long orderId, OrderDTO orderDTO) {
        Customer customer = customerDAO.findOne(customerId);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer with this ID not found");
        }
        Order order = orderDAO.findOne(orderId);
        if (order == null) {
            throw new ResourceNotFoundException("Order with this ID not found");
        }
//        order.setOrderId(orderId);
        order.setCustomer(customer);
        order.setOrderDate(orderDTO.getOrderDate());
        order.setRequiredDate(orderDTO.getRequiredDate());
        order.setShippedDate(orderDTO.getShippedDate());
        order.setStatus(orderDTO.getStatus());

        // Maps each member of collection containing requests to the class
        List<OrderDetails> orderDetailsList = ObjectMapperUtils
                .mapAll(orderDTO.getOrderDetailsDTO(), OrderDetails.class);

        int counter = 0;
        for (OrderDetails orderDetails : orderDetailsList) {
            Product product = productDAO.findByCode(orderDTO.getOrderDetailsDTO().get(counter).getProductCode());
            orderDetails.setOrder(order);
            orderDetails.setProduct(product);
            orderDetails.setQuantityOrdered(orderDTO.getOrderDetailsDTO().get(counter).getQuantityOrdered());
            orderDetails.setPrice(orderDTO.getOrderDetailsDTO().get(counter).getPrice());
            counter++;
        }

        order.setOrderDetails(orderDetailsList);

        orderDAO.update(order);

        return orderDTO;
    }

    /**
     * Method to delete order by number taken from the REST controller
     *
     * @param orderId get oder number from th REST controller
     * @return Order entity as a response
     */
    // FIXME: Set OrderDetailsDTO to the OrderDTO as a response
    public OrderDTO deleteById(Long orderId) {
        Order order = orderDAO.findOne(orderId);
        if (order == null) {
            throw new ResourceNotFoundException("Order with this ID not found");
        }

        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

        List<OrderDetails> orderDetails = orderDetailsDAO.findAllByOrderId(orderId);
        List<OrderDetailsDTO> orderDetailsDTOList = ObjectMapperUtils.mapAll(orderDetails, OrderDetailsDTO.class);

        orderDTO.setOrderDetailsDTO(orderDetailsDTOList);

        orderDAO.delete(order);
        return orderDTO;
    }

    /**
     * Delete order method
     *
     * @param customerId get customer number form the REST controller
     * @param orderId    get order number form the REST controller
     * @return Order entity as a response
     */
    // FIXME: So that it was able to delete entity by customer and order numbers
    public OrderDTO deleteByCustomerIdAndOrderId(Long customerId, Long orderId) {

        Order order = orderDAO.findOne(orderId);
        if (order == null) {
            throw new ResourceNotFoundException("Order with this ID not found");
        }
        orderDAO.delete(order);

        return modelMapper.map(order, OrderDTO.class);
    }

    /**
     * Util method to get order by specific parameter
     *
     * @param orders Gets list of the order entities
     * @return List of the order DTOs
     */
    private List<OrderDTO> findOrdersByListWithParam(List<Order> orders) {
        List<OrderDTO> orderDTOList = new LinkedList<>();

        for (Order order : orders) {
            // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

            List<OrderDetails> orderDetailsList =
                    orderDetailsDAO.findAllByOrderId(orderDTO.getOrderId());

            List<OrderDetailsDTO> orderDetailsDTOList = new ArrayList<>();
            orderDetailsList.forEach(orderDetails ->
                    orderDetailsDTOList.add(
                            modelMapper.map(orderDetails, OrderDetailsDTO.class)
                    )
            );
            for (OrderDetails orderDetails : orderDetailsList) {
                // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
                OrderDetailsDTO orderDetailsDTO = modelMapper.map(orderDetails, OrderDetailsDTO.class);
                orderDetailsDTOList.add(orderDetailsDTO);
            }

            orderDTO.setOrderDetailsDTO(orderDetailsDTOList);
            orderDTOList.add(orderDTO);
        }

        return orderDTOList;
    }
}
