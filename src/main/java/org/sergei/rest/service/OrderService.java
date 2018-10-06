package org.sergei.rest.service;

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
import org.sergei.rest.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class OrderService {

    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(ModelMapper modelMapper, OrderRepository orderRepository,
                        CustomerRepository customerRepository, OrderDetailsRepository orderDetailsRepository,
                        ProductRepository productRepository) {
        this.modelMapper = modelMapper;
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
        // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
        OrderDTO orderDTOResponse = modelMapper.map(order, OrderDTO.class);

        List<OrderDetails> orderDetailsList =
                orderDetailsRepository.findAllByOrderNumber(orderDTOResponse.getOrderNumber());

        List<OrderDetailsDTO> orderDetailsDTOS = new ArrayList<>();
        for (OrderDetails orderDetails : orderDetailsList) {
            // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
            OrderDetailsDTO orderDetailsDTO = modelMapper.map(orderDetails, OrderDetailsDTO.class);
            orderDetailsDTOS.add(orderDetailsDTO);
        }

        orderDTOResponse.setOrderDetailsDTO(orderDetailsDTOS);

        return orderDTOResponse;
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
            // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

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
        Customer customer = customerRepository.findById(customerNumber)
                .orElseThrow(() -> new RecordNotFoundException("No customer with this number found"));

        Order order = modelMapper.map(orderDTORequestBody, Order.class);
        order.setCustomer(customer);

        // Maps each member of collection containing requests to the class
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
    // FIXME: StackOverflowException

    /**
     * Update order by customer and order numbers
     *
     * @param customerNumber      get customer number form the REST controller
     * @param orderNumber         get order number form the REST controller
     * @param orderDTORequestBody Get order DTO request body
     * @return return order DTO as a response
     */

    public OrderDTO updateOrder(Long customerNumber, Long orderNumber, OrderDTO orderDTORequestBody) {
        Customer customer = customerRepository.findById(customerNumber)
                .orElseThrow(() -> new RecordNotFoundException("No customer with this number found"));

        Order order = orderRepository.findById(orderNumber)
                .orElseThrow(() -> new RecordNotFoundException("Order with this number not found"));
        order.setOrderNumber(orderDTORequestBody.getOrderNumber());
        order.setCustomer(customer);
        order.setOrderDate(orderDTORequestBody.getOrderDate());
        order.setRequiredDate(orderDTORequestBody.getRequiredDate());
        order.setShippedDate(orderDTORequestBody.getShippedDate());
        order.setStatus(orderDTORequestBody.getStatus());

        // Maps each member of collection containing requests to the class
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

        // FIXME: StackOverflowException
        orderRepository.save(order);

/*        return orderRepository.findById(orderNumber).map(order -> {
            order.setOrderNumber(orderDTORequestBody.getOrderNumber());
            order.setOrderDate(orderDTORequestBody.getOrderDate());
            order.setRequiredDate(orderDTORequestBody.getRequiredDate());
            order.setShippedDate(orderDTORequestBody.getShippedDate());
            order.setStatus(orderDTORequestBody.getStatus());
            return orderRepository.save(order);
        }).orElseThrow(() -> new RecordNotFoundException("Order with this number not found"));*/

        return orderDTORequestBody;
    }

    /**
     * Method to delete order by number taken from the REST controller
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
    public OrderDTO deleteOrderByCustomerIdAndOrderId(Long customerNumber, Long orderNumber) {
        if (!customerRepository.existsById(customerNumber)) {
            throw new RecordNotFoundException("No customer with this number found");
        }

        Order order = orderRepository.findById(orderNumber)
                .orElseThrow(() -> new RecordNotFoundException("Order with this number not found"));

        orderRepository.delete(order);

        return modelMapper.map(order, OrderDTO.class);
    }
}
