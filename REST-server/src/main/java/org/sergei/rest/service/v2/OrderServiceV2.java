/*
 * Copyright 2018-2019 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.sergei.rest.service.v2;

import org.sergei.rest.dto.OrderDetailsDTO;
import org.sergei.rest.dto.v2.OrderDTOV2;
import org.sergei.rest.exceptions.ResourceNotFoundException;
import org.sergei.rest.model.Order;
import org.sergei.rest.model.OrderDetails;
import org.sergei.rest.repository.CustomerRepository;
import org.sergei.rest.repository.OrderDetailsRepository;
import org.sergei.rest.repository.OrderRepository;
import org.sergei.rest.repository.ProductRepository;
import org.sergei.rest.service.OrderService;
import org.sergei.rest.service.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.sergei.rest.util.ObjectMapperUtil.map;
import static org.sergei.rest.util.ObjectMapperUtil.mapAllPages;

/**
 * V2 of order service
 *
 * @author Sergei Visotsky
 */
@Service
public class OrderServiceV2 extends OrderService {

    public OrderServiceV2(OrderRepository orderRepository,
                          OrderDetailsRepository orderDetailsRepository,
                          CustomerRepository customerRepository, ProductRepository productRepository) {
        super(orderRepository, orderDetailsRepository, customerRepository, productRepository);
    }

    /**
     * Get order by ID
     *
     * @param orderId get order number as a parameter from REST controller
     * @return order DTO response
     */
    private OrderDTOV2 findOneV2(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Constants.ORDER_NOT_FOUND)
                );
        // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
        OrderDTOV2 orderDTOV2 = map(order, OrderDTOV2.class);

        List<OrderDetails> orderDetailsList =
                orderDetailsRepository.findAllByOrderId(orderDTOV2.getOrderId());

        List<OrderDetailsDTO> orderDetailsDTOList = new ArrayList<>();
        orderDetailsList.forEach(orderDetails ->
                orderDetailsDTOList.add(
                        map(orderDetails, OrderDetailsDTO.class)
                )
        );

        orderDTOV2.setOrderDetailsDTO(orderDetailsDTOList);

        return orderDTOV2;
    }

    /**
     * Get order by customer and order IDs
     *
     * @param customerId Get customer ID from the REST controller
     * @param orderId    Get order ID from the REST controller
     * @return Return order DTO response
     */
    public OrderDTOV2 findOneByCustomerIdAndOrderIdV2(Long customerId, Long orderId) {
        customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Constants.CUSTOMER_NOT_FOUND)
                );
        return findOneV2(orderId);
    }

    /**
     * Get all orders by customer ID
     *
     * @param customerId customer ID form the REST controller
     * @return List of order DTOs
     */
    public List<OrderDTOV2> findAllByCustomerIdV2(Long customerId) {
        List<Order> orders = orderRepository.findAllByCustomerId(customerId);
        if (orders == null) {
            throw new ResourceNotFoundException(Constants.ORDER_NOT_FOUND);
        }

        return findOrdersByListWithParamV2(orders);
    }

    /**
     * Get all orders by customer ID paginated
     *
     * @param customerId customer ID form the REST controller
     * @return List of order DTOs
     */
    public Page<OrderDTOV2> findAllByCustomerIdPaginatedV2(Long customerId, int page, int size) {
        Page<Order> orders = orderRepository.findAllByCustomerPaginatedId(customerId, PageRequest.of(page, size));
        if (orders == null) {
            throw new ResourceNotFoundException(Constants.ORDER_NOT_FOUND);
        }

        return findOrdersByListWithParamPaginatedV2(orders);
    }

    /**
     * Get all orders by product code
     *
     * @param productCode get product code from the REST controller
     * @return return list of order DTOs
     */
    public List<OrderDTOV2> findAllByProductCodeV2(String productCode) {
        List<Order> orders = orderRepository.findAllByProductCode(productCode);
        if (orders == null) {
            throw new ResourceNotFoundException(Constants.ORDER_NOT_FOUND);
        }
        return findOrdersByListWithParamV2(orders);
    }

    /**
     * Util method to get order by specific parameter paginated
     *
     * @param orders Gets list of the order entities
     * @return List of the order DTOs
     */
    private Page<OrderDTOV2> findOrdersByListWithParamPaginatedV2(Page<Order> orders) {
        orders.forEach(order ->
                {
                    // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
                    OrderDTOV2 orderDTO = map(order, OrderDTOV2.class);
                    orderDTO.setCustomerId(order.getCustomer().getCustomerId());

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
                }
        );
        return mapAllPages(orders, OrderDTOV2.class);
    }

    /**
     * Util method to get order by specific parameter
     *
     * @param orders Gets list of the order entities
     * @return List of the order DTOs
     */
    private List<OrderDTOV2> findOrdersByListWithParamV2(List<Order> orders) {
        List<OrderDTOV2> orderDTOList = new LinkedList<>();
        orders.forEach(order ->
                {
                    // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
                    OrderDTOV2 orderDTOV2 = map(order, OrderDTOV2.class);
                    orderDTOV2.setCustomerId(order.getCustomer().getCustomerId());

                    List<OrderDetails> orderDetailsList =
                            orderDetailsRepository.findAllByOrderId(orderDTOV2.getOrderId());

                    List<OrderDetailsDTO> orderDetailsDTOList = new ArrayList<>();
                    orderDetailsList.forEach(orderDetails ->
                            {
                                // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
                                OrderDetailsDTO orderDetailsDTO = map(orderDetails, OrderDetailsDTO.class);
                                orderDetailsDTO.setProductCode(orderDetails.getProduct().getProductCode());
                                orderDetailsDTOList.add(orderDetailsDTO);
                            }
                    );
                    orderDTOV2.setOrderDetailsDTO(orderDetailsDTOList);
                    orderDTOList.add(orderDTOV2);
                }
        );
        return orderDTOList;
    }
}
