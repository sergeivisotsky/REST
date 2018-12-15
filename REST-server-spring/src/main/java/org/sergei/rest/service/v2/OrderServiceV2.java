package org.sergei.rest.service.v2;

import org.modelmapper.ModelMapper;
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
import org.sergei.rest.utils.ObjectMapperUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sergei Visotsky
 * @since 12/9/2018
 * <p>
 * V2 of order service
 */
@Service
public class OrderServiceV2 extends OrderService {

    public OrderServiceV2(OrderRepository orderRepository,
                          OrderDetailsRepository orderDetailsRepository,
                          CustomerRepository customerRepository, ProductRepository productRepository) {
        super(orderRepository, orderDetailsRepository, customerRepository, productRepository);
    }

    /**
     * Get order by number
     *
     * @param orderId get order number as a parameter from REST controller
     * @return order DTO response
     */
    private OrderDTOV2 findOneV2(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(ORDER_NOT_FOUND)
                );
        // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
        OrderDTOV2 orderDTOV2 = ObjectMapperUtils.map(order, OrderDTOV2.class);

        List<OrderDetails> orderDetailsList =
                orderDetailsRepository.findAllByOrderId(orderDTOV2.getOrderId());

        List<OrderDetailsDTO> orderDetailsDTOList = new ArrayList<>();
        orderDetailsList.forEach(orderDetails ->
                orderDetailsDTOList.add(
                        ObjectMapperUtils.map(orderDetails, OrderDetailsDTO.class)
                )
        );

        orderDTOV2.setOrderDetailsDTO(orderDetailsDTOList);

        return orderDTOV2;
    }

    /**
     * Get order by customer and order numbers
     *
     * @param customerId Get customer number from the REST controller
     * @param orderId    Get order number from the REST controller
     * @return Return order DTO reponse
     */
    public OrderDTOV2 findOneByCustomerIdAndOrderIdV2(Long customerId, Long orderId) {
        customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
                );
        return findOneV2(orderId);
    }

    /**
     * Get all orders by customer number
     *
     * @param customerId customer number form the REST controller
     * @return List of order DTOs
     */
    public List<OrderDTOV2> findAllByCustomerIdV2(Long customerId) {
        List<Order> orders = orderRepository.findAllByCustomerId(customerId);
        if (orders == null) {
            throw new ResourceNotFoundException(ORDER_NOT_FOUND);
        }

        return findOrdersByListWithParamV2(orders);
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
            throw new ResourceNotFoundException(ORDER_NOT_FOUND);
        }
        return findOrdersByListWithParamV2(orders);
    }

    /**
     * Util method to get order by specific parameter
     *
     * @param orders Gets list of the order entities
     * @return List of the order DTOs
     */
    private List<OrderDTOV2> findOrdersByListWithParamV2(List<Order> orders) {
        List<OrderDTOV2> orderDTOList = new LinkedList<>();

        for (Order order : orders) {
            // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
            OrderDTOV2 orderDTO = ObjectMapperUtils.map(order, OrderDTOV2.class);

            List<OrderDetails> orderDetailsList =
                    orderDetailsRepository.findAllByOrderId(orderDTO.getOrderId());

            List<OrderDetailsDTO> orderDetailsDTOList = new ArrayList<>();
            orderDetailsList.forEach(orderDetails ->
                    orderDetailsDTOList.add(
                            ObjectMapperUtils.map(orderDetails, OrderDetailsDTO.class)
                    )
            );
            for (OrderDetails orderDetails : orderDetailsList) {
                // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
                OrderDetailsDTO orderDetailsDTO = ObjectMapperUtils.map(orderDetails, OrderDetailsDTO.class);
                orderDetailsDTOList.add(orderDetailsDTO);
            }

            orderDTO.setOrderDetailsDTO(orderDetailsDTOList);
            orderDTOList.add(orderDTO);
        }

        return orderDTOList;
    }
}
