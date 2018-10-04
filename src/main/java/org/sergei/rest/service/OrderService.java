package org.sergei.rest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.sergei.rest.dto.OrderDTO;
import org.sergei.rest.dto.OrderDetailsDTO;
import org.sergei.rest.exceptions.RecordNotFoundException;
import org.sergei.rest.model.Customer;
import org.sergei.rest.model.Order;
import org.sergei.rest.model.OrderDetails;
import org.sergei.rest.model.Product;
import org.sergei.rest.repository.CustomerRepository;
import org.sergei.rest.repository.OrderDetailsRepository;
import org.sergei.rest.repository.OrderRepository;
import org.sergei.rest.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class OrderService {

    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(ModelMapper modelMapper, ObjectMapper objectMapper, OrderRepository orderRepository,
                        CustomerRepository customerRepository, OrderDetailsRepository orderDetailsRepository,
                        ProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.productRepository = productRepository;
    }

    /**
     * Get all orders
     *
     * @return List of order DTOs
     */
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return getOrdersByListWithParam(orders);
    }

    /**
     * Get order by number
     *
     * @param orderNumber get order number as a parameter from REST controller
     * @return order DTO response
     */
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
            // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
            OrderDetailsDTO orderDetailsDTO = modelMapper.map(orderDetails, OrderDetailsDTO.class);
            orderDetailsDTOS.add(orderDetailsDTO);
        }

        response.setOrderDetailsDTO(orderDetailsDTOS);

        return response;
    }

    /**
     * Get order by customer and order numbers
     *
     * @param customerNumber Get customer number from the REST controller
     * @param orderNumber    Get order number from the REST controller
     * @return Return order DTO reponse
     */
    public OrderDTO getOrderByCustomerAndOrderNumbers(Long customerNumber, Long orderNumber) {
        if (!customerRepository.existsById(customerNumber)) {
            throw new RecordNotFoundException("No customer with this number found");
        }
        return getOrderByNumber(orderNumber);
    }

    /**
     * Get all orders by customer number
     *
     * @param customerNumber customer number form the REST controller
     * @return List of order DTOs
     */
    public List<OrderDTO> getAllOrdersByCustomerNumber(Long customerNumber) {
        if (!customerRepository.existsById(customerNumber)) {
            throw new RecordNotFoundException("No customer with this number found");
        }
        List<Order> orders = orderRepository.findAllByCustomerNumber(customerNumber);

        return getOrdersByListWithParam(orders);
    }

    /**
     * Get all orders by product code
     *
     * @param productCode get product code from the REST controller
     * @return return list of order DTOs
     */
    public List<OrderDTO> getAllByProductCode(String productCode) {
        List<Order> orders = orderRepository.findAllByProductCode(productCode);
        return getOrdersByListWithParam(orders);
    }

    /**
     * Service class to get order by specific parameter
     *
     * @param orders Gets list of the order entities
     * @return List of the order DTOs
     */
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
                // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
                OrderDetailsDTO orderDetailsDTO = modelMapper.map(orderDetails, OrderDetailsDTO.class);
                orderDetailsDTOS.add(orderDetailsDTO);
            }

            orderDTO.setOrderDetailsDTO(orderDetailsDTOS);
            response.add(orderDTO);
        }

        return response;
    }

    /**
     * Save order
     *
     * @param customerNumber      Get customer number from the REST controller
     * @param orderDTORequestBody Get order DTO request body
     * @return return order DTO as a response
     */
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

        List<OrderDetails> orderDetailsList = ObjectMapperUtils
                .mapAll(orderDTORequestBody.getOrderDetailsDTO(), OrderDetails.class);

        int counter = 0;
        for (OrderDetails orderDetails : orderDetailsList) {
            Product product = productRepository.findByCode(orderDTORequestBody.getOrderDetailsDTO().get(counter).getProductCode())
                    .orElseThrow(() -> new RecordNotFoundException("Product with this code not found"));
            orderDetails.setOrder(order);
            orderDetails.setProduct(product);
            orderDetails.setQuantityOrdered(orderDTORequestBody.getOrderDetailsDTO().get(counter).getQuantityOrdered());
            orderDetails.setPrice(orderDTORequestBody.getOrderDetailsDTO().get(counter).getPrice());
            counter++;
        }

        order.setOrderDetails(orderDetailsList);

        orderRepository.save(order);

        return orderDTORequestBody;
    }


    // TODO: So that order and its details be updated
    /**
     * Update order by customer and order numbers
     *
     * @param customerNumber get customer number form the REST controller
     * @param orderNumber    get order number form the REST controller
     * @param orderRequest   Get order DTO request body
     * @return return order DTO as a response
     */
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

    /**
     * Metod to delete order by number taken from the REST controller
     *
     * @param orderNumber get oder number from th REST controller
     * @return Order entity as a response
     */
    // TODO: Replace of the entity response to the DTO response
    public Order deleteOrderByNumber(Long orderNumber) {
        return orderRepository.findById(orderNumber).map(order -> {
            orderRepository.delete(order);
            return order;
        }).orElseThrow(() -> new RecordNotFoundException("Order with this number not found"));
    }

    /**
     * Delete order method
     *
     * @param customerNumber get customer number form the REST controller
     * @param orderNumber    get order number form the REST controller
     * @return Order entity as a response
     */
    // TODO: Replace of the entity response to the DTO response
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
