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

package org.sergei.rest.service.util;

import org.sergei.rest.dto.OrderDTO;
import org.sergei.rest.dto.OrderDetailsDTO;
import org.sergei.rest.dto.v2.OrderDTOV2;
import org.sergei.rest.exceptions.ResourceNotFoundException;
import org.sergei.rest.model.Order;
import org.sergei.rest.model.OrderDetails;
import org.sergei.rest.model.Product;
import org.sergei.rest.repository.OrderDetailsRepository;
import org.sergei.rest.repository.ProductRepository;
import org.sergei.rest.service.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.sergei.rest.util.ObjectMapperUtil.map;
import static org.sergei.rest.util.ObjectMapperUtil.mapAllPages;

/**
 * @author Sergei Visotsky
 */
@Component
public class ServiceComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceComponent.class);

    private final OrderDetailsRepository orderDetailsRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ServiceComponent(OrderDetailsRepository orderDetailsRepository, ProductRepository productRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
        this.productRepository = productRepository;
    }

    /**
     * Method to map order details list DTO to the entity list
     *
     * @param order    entity
     * @param orderDTO payload DTO
     * @return list with details
     */
    public List<OrderDetails> mapOrderDetailsToDTO(Order order, OrderDTO orderDTO) {
        // Maps each member of collection containing requests to the class
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAllByOrderId(orderDTO.getOrderId());

        int counter = 0;
        for (OrderDetails orderDetails : orderDetailsList) {
            Product product = productRepository.findByProductCode(orderDTO.getOrderDetailsDTO().get(counter).getProductCode())
                    .orElseThrow(
                            () -> new ResourceNotFoundException(Constants.PRODUCT_NOT_FOUND)
                    );
            orderDetails.setOrder(order);
            orderDetails.setProduct(product);
            orderDetails.setQuantityOrdered(orderDTO.getOrderDetailsDTO().get(counter).getQuantityOrdered());
            orderDetails.setPrice(orderDTO.getOrderDetailsDTO().get(counter).getPrice());
            counter++;
        }

        return orderDetailsList;
    }

    /**
     * Util method to get order by specific parameter
     *
     * @param orders Gets list of the order entities
     * @return List of the order DTOs
     */
    public List<OrderDTO> findOrdersByListWithParam(List<Order> orders) {
        List<OrderDTO> orderDTOList = new LinkedList<>();
        orders.forEach(order ->
                {
                    LOGGER.debug("Customer ID who made order in entity: {}", order.getCustomer().getCustomerId());
                    // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
                    OrderDTO orderDTO = map(order, OrderDTO.class);
                    orderDTO.setCustomerId(order.getCustomer().getCustomerId());
                    LOGGER.debug("Customer ID who made order in DTO: {}", orderDTO.getCustomerId());

                    List<OrderDetails> orderDetailsList =
                            orderDetailsRepository.findAllByOrderId(orderDTO.getOrderId());

                    List<OrderDetailsDTO> orderDetailsDTOList = new ArrayList<>();
                    orderDetailsList.forEach(orderDetails ->
                            {
                                // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
                                OrderDetailsDTO orderDetailsDTO = map(orderDetails, OrderDetailsDTO.class);
                                orderDetailsDTO.setProductCode(orderDetails.getProduct().getProductCode());
                                LOGGER.debug("Product code in order details: {}", orderDetailsDTO.getProductCode());
                                orderDetailsDTOList.add(orderDetailsDTO);
                            }
                    );

                    orderDTO.setOrderDetailsDTO(orderDetailsDTOList);
                    orderDTOList.add(orderDTO);
                }
        );
        return orderDTOList;
    }

    /**
     * Util method to get order by specific parameter paginated
     *
     * @param orders Gets list of the order entities
     * @return List of the order DTOs
     */
    public Page<OrderDTOV2> findOrdersByListWithParamPaginatedV2(Page<Order> orders) {
        orders.forEach(order ->
                {
                    // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
                    OrderDTOV2 orderDTO = map(order, OrderDTOV2.class);
                    orderDTO.setCustomerId(order.getCustomer().getCustomerId());

                    orderDTO.setOrderDetailsDTO(
                            setOrderDetailsDTOToPojo(orderDTO.getOrderId())
                    );
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
    public List<OrderDTOV2> findOrdersByListWithParamV2(List<Order> orders) {
        List<OrderDTOV2> orderDTOList = new LinkedList<>();
        orders.forEach(order ->
                {
                    // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
                    OrderDTOV2 orderDTOV2 = map(order, OrderDTOV2.class);
                    orderDTOV2.setCustomerId(order.getCustomer().getCustomerId());

                    orderDTOV2.setOrderDetailsDTO(
                            setOrderDetailsDTOToPojo(orderDTOV2.getOrderId())
                    );
                    orderDTOList.add(orderDTOV2);
                }
        );
        return orderDTOList;
    }

    private List<OrderDetailsDTO> setOrderDetailsDTOToPojo(Long orderId) {
        List<OrderDetails> orderDetailsList =
                orderDetailsRepository.findAllByOrderId(orderId);

        List<OrderDetailsDTO> orderDetailsDTOList = new ArrayList<>();
        orderDetailsList.forEach(orderDetails ->
                {
                    // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
                    OrderDetailsDTO orderDetailsDTO = map(orderDetails, OrderDetailsDTO.class);
                    orderDetailsDTO.setProductCode(orderDetails.getProduct().getProductCode());
                    orderDetailsDTOList.add(orderDetailsDTO);
                }
        );

        return orderDetailsDTOList;
    }
}
