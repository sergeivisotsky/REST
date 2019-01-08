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
import org.sergei.rest.util.ServiceConstants;
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

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderDetailsRepository orderDetailsRepository,
                        CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
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
                        () -> new ResourceNotFoundException(ServiceConstants.CUSTOMER_NOT_FOUND)
                );
        Order order = orderRepository.findById(orderId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(ServiceConstants.ORDER_NOT_FOUND)
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
            throw new ResourceNotFoundException(ServiceConstants.ORDER_NOT_FOUND);
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
        List<Order> orders = orderRepository.findAllByProductCode(productCode);
        if (orders == null) {
            throw new ResourceNotFoundException(ServiceConstants.ORDER_NOT_FOUND);
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
    public OrderDTO save(Long customerId, OrderDTO orderDTO) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException(ServiceConstants.CUSTOMER_NOT_FOUND)
        );
        LOGGER.debug("Customer is: {}", customer.getCustomerId());
        Order order = map(orderDTO, Order.class);
        order.setCustomer(customer);

        List<OrderDetails> orderDetailsList = setOrderDetailsList(order, orderDTO);
        order.setOrderDetails(orderDetailsList);

        Order savedOrder = orderRepository.save(order);
        return map(savedOrder, OrderDTO.class);
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
                        () -> new ResourceNotFoundException(ServiceConstants.CUSTOMER_NOT_FOUND)
                );
        Order order = orderRepository.findById(orderId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(ServiceConstants.ORDER_NOT_FOUND)
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

        List<OrderDetails> orderDetailsList = setOrderDetailsList(order, orderDTO);

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
    public void deleteByCustomerIdAndOrderId(Long customerId, Long orderId) {
        orderRepository.deleteByCustomerIdAndOrderId(customerId, orderId);
    }

    /**
     * Method to map order details list DTO to the entity list
     *
     * @param order    entity
     * @param orderDTO payload DTO
     * @return list with details
     */
    private List<OrderDetails> setOrderDetailsList(Order order, OrderDTO orderDTO) {
        // Maps each member of collection containing requests to the class
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAllByOrderId(orderDTO.getOrderId());

        int counter = 0;
        for (OrderDetails orderDetails : orderDetailsList) {
            Product product = productRepository.findByProductCode(orderDTO.getOrderDetailsDTO().get(counter).getProductCode())
                    .orElseThrow(
                            () -> new ResourceNotFoundException(ServiceConstants.PRODUCT_NOT_FOUND)
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
    private List<OrderDTO> findOrdersByListWithParam(List<Order> orders) {
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
}
