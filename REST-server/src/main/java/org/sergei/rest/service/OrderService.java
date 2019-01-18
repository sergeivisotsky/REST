/*
 * Copyright 2018-2019 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sergei.rest.service;

import org.sergei.rest.dto.OrderDTO;
import org.sergei.rest.dto.OrderDetailsDTO;
import org.sergei.rest.exceptions.ResourceNotFoundException;
import org.sergei.rest.model.Customer;
import org.sergei.rest.model.Order;
import org.sergei.rest.model.OrderDetails;
import org.sergei.rest.model.Product;
import org.sergei.rest.repository.CustomerRepository;
import org.sergei.rest.repository.OrderDetailsRepository;
import org.sergei.rest.repository.OrderRepository;
import org.sergei.rest.repository.ProductRepository;
import org.sergei.rest.service.util.ServiceComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.sergei.rest.util.ObjectMapperUtil.map;

/**
 * @author Sergei Visotsky
 */
@Service
public class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    protected final OrderRepository orderRepository;
    protected final OrderDetailsRepository orderDetailsRepository;
    protected final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    protected final ServiceComponent serviceComponent;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderDetailsRepository orderDetailsRepository,
                        CustomerRepository customerRepository, ProductRepository productRepository, ServiceComponent serviceComponent) {
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.serviceComponent = serviceComponent;
    }

    /**
     * Get order by number
     *
     * @param customerId Get customer ID from the REST controller
     * @param orderId    get order number as a parameter from REST controller
     * @return order DTO response
     */
    public OrderDTO findOne(Long customerId, Long orderId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Constants.CUSTOMER_NOT_FOUND)
                );
        Order order = orderRepository.findById(orderId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Constants.ORDER_NOT_FOUND)
                );
        // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
        OrderDTO orderDTO = map(order, OrderDTO.class);
        orderDTO.setCustomerId(customer.getCustomerId());

        List<OrderDetails> orderDetailsList =
                orderDetailsRepository.findAllByOrderId(orderDTO.getOrderId());

        List<OrderDetailsDTO> orderDetailsDTOList = new ArrayList<>();
        orderDetailsList.forEach(orderDetails ->
                {
                    // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
                    OrderDetailsDTO orderDetailsDTO = map(orderDetails, OrderDetailsDTO.class);
                    orderDetailsDTO.setProductCode(orderDetails.getProduct().getProductCode());
                    orderDetailsDTOList.add(orderDetailsDTO);
                }
        );

        orderDTO.setOrderDetailsDTO(orderDetailsDTOList);

        return orderDTO;
    }

    /**
     * Get all orders by customer ID
     *
     * @param customerId customer ID form the REST controller
     * @return List of order DTOs
     */
    public List<OrderDTO> findAll(Long customerId) {
        List<Order> orders = orderRepository.findAllByCustomerId(customerId);
        if (orders == null) {
            throw new ResourceNotFoundException(Constants.ORDER_NOT_FOUND);
        }
        return serviceComponent.findOrdersByListWithParam(orders);
    }

    /**
     * Get all orders by product code
     *
     * @param productCode get product code from the REST controller
     * @return return list of order DTOs
     */
    public List<OrderDTO> findAllByProductCode(String productCode) {
        List<Order> orders = orderRepository.findAllByProductCode(productCode);
        if (orders == null) {
            throw new ResourceNotFoundException(Constants.ORDER_NOT_FOUND);
        }
        return serviceComponent.findOrdersByListWithParam(orders);
    }

    /**
     * Save order and its details
     *
     * @param customerId Get customer number from the REST controller
     * @param orderDTO   Get order DTO request body
     * @return return order DTO as a response
     */
    public OrderDTO save(Long customerId, OrderDTO orderDTO) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException(Constants.CUSTOMER_NOT_FOUND)
        );
        LOGGER.debug("Customer id: {}", customer.getCustomerId());
        Order order = map(orderDTO, Order.class);
        order.setCustomer(customer);

        List<OrderDetails> orderDetailsList = new LinkedList<>();

        final int index = 0;

        OrderDetails orderDetails = new OrderDetails();
        Product product = productRepository.findByProductCode(
                orderDTO.getOrderDetailsDTO().get(index).getProductCode())
                .orElseThrow(
                        () -> new ResourceNotFoundException(Constants.PRODUCT_NOT_FOUND)
                );
        orderDetails.setProduct(product);
        orderDetails.setQuantityOrdered(orderDTO.getOrderDetailsDTO().get(index).getQuantityOrdered());
        orderDetails.setPrice(orderDTO.getOrderDetailsDTO().get(index).getPrice());
        orderDetails.setOrder(order);
        orderDetailsList.add(orderDetails);

        order.setOrderDetails(orderDetailsList);

        Order savedOrder = orderRepository.save(order);

        orderDTO.setOrderId(savedOrder.getOrderId());
        orderDTO.setCustomerId(customer.getCustomerId());

        return orderDTO;
    }

    /**
     * Update order by customer and order IDs
     *
     * @param customerId get customer ID form the REST controller
     * @param orderId    get order ID form the REST controller
     * @param orderDTO   Get order DTO request body
     * @return return order DTO as a response
     */
    public OrderDTO update(Long customerId, Long orderId, OrderDTO orderDTO) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Constants.CUSTOMER_NOT_FOUND)
                );
        Order order = orderRepository.findById(orderId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Constants.ORDER_NOT_FOUND)
                );
        order.setCustomer(customer);
        order.setOrderDate(orderDTO.getOrderDate());
        order.setRequiredDate(orderDTO.getRequiredDate());
        order.setShippedDate(orderDTO.getShippedDate());
        order.setStatus(orderDTO.getStatus());

        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setCustomerId(customer.getCustomerId());

        // clear existing children list so that they are removed from database
        order.getOrderDetails().clear();

        List<OrderDetails> orderDetailsList = serviceComponent.mapOrderDetailsToDTO(order, orderDTO);

        // add the new children list created above to the existing list
        order.getOrderDetails().addAll(orderDetailsList);

        orderRepository.save(order);

        return orderDTO;
    }

    /**
     * Delete order method
     *
     * @param customerId get customer ID form the REST controller
     * @param orderId    get order ID form the REST controller
     */
    public void delete(Long customerId, Long orderId) {
        Order order = orderRepository.findByCustomerIdAndOrderId(customerId, orderId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Constants.ORDER_NOT_FOUND)
                );
        LOGGER.debug("Customer ID whose order gonna be deleted: {}", order.getCustomer().getCustomerId());
        LOGGER.debug("Order ID which should be deleted: {}", order.getOrderId());
        orderRepository.delete(
                order.getCustomer().getCustomerId(),
                order.getOrderId()
        );
    }
}
