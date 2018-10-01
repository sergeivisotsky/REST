package org.sergei.rest.service;

import org.modelmapper.ModelMapper;
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
public class CustomerService {

    private final ModelMapper modelMapper;

    private final CustomerRepository customerRepository;

    private final OrderRepository orderRepository;

    private final OrderDetailsRepository orderDetailsRepository;

    @Autowired
    public CustomerService(ModelMapper modelMapper,
                           CustomerRepository customerRepository,
                           OrderRepository orderRepository,
                           OrderDetailsRepository orderDetailsRepository) {
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    // TODO: Replace with ModelMapper
    // Get all customers
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> customerDTOResponse = new LinkedList<>();

        List<Customer> customers = customerRepository.findAll();

        for (Customer customer : customers) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setCustomerNumber(customer.getCustomerNumber());
            customerDTO.setFirstName(customer.getFirstName());
            customerDTO.setLastName(customer.getLastName());
            customerDTO.setAge(customer.getAge());
//            CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);

            List<Order> orders = orderRepository.findAllByCustomerNumber(customer.getCustomerNumber());

            List<OrderDTO> orderDTOList = new LinkedList<>();
            for (Order order : orders) {
                OrderDTO orderDTO = new OrderDTO();

                orderDTO.setOrderNumber(order.getOrderNumber());
                orderDTO.setOrderDate(order.getOrderDate());
                orderDTO.setRequiredDate(order.getRequiredDate());
                orderDTO.setShippedDate(order.getShippedDate());
                orderDTO.setStatus(order.getStatus());

//                OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

                orderDTOList.add(orderDTO);

                List<OrderDetails> orderDetailsList =
                        orderDetailsRepository.findAllByOrderNumber(orderDTO.getOrderNumber());

                List<OrderDetailsDTO> orderDetailsDTOS = new ArrayList<>();
                for (OrderDetails orderDetails : orderDetailsList) {
                    OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();

                    orderDetailsDTO.setProductCode(orderDetails.getProduct().getProductCode());
                    orderDetailsDTO.setPrice(orderDetails.getPrice());
                    orderDetailsDTO.setQuantityOrdered(orderDetails.getQuantityOrdered());
//                    OrderDetailsDTO orderDetailsDTO = modelMapper.map(orderDetails, OrderDetailsDTO.class);

                    orderDetailsDTOS.add(orderDetailsDTO);
                }

                orderDTO.setOrderDetailsDTO(orderDetailsDTOS);
            }

            customerDTO.setOrders(orderDTOList);
            customerDTOResponse.add(customerDTO);
        }

        return customerDTOResponse;
    }

    // Get customer by number
    public CustomerDTO getCustomerByNumber(Long customerNumber) {
        Customer customer = customerRepository.findById(customerNumber)
                .orElseThrow(() -> new RecordNotFoundException("Customer with this number not found"));
        CustomerDTO response = new CustomerDTO();

        response.setCustomerNumber(customer.getCustomerNumber());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setAge(customer.getAge());

        List<Order> orders = orderRepository.findAllByCustomerNumber(customerNumber);

        List<OrderDTO> orderDTOList = new LinkedList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = new OrderDTO();

            orderDTO.setOrderNumber(order.getOrderNumber());
            orderDTO.setOrderDate(order.getOrderDate());
            orderDTO.setRequiredDate(order.getRequiredDate());
            orderDTO.setShippedDate(order.getShippedDate());
            orderDTO.setStatus(order.getStatus());

            orderDTOList.add(orderDTO);

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
        }

        response.setOrders(orderDTOList);

        return response;
    }

    // Save customer
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    // TODO: Save customer and his orders with details in all request body

    // Update customer by customer number
    public Customer updateCustomer(Long customerNumber, Customer customerRequest) {
        return customerRepository.findById(customerNumber)
                .map(customer -> {
                    customer.setCustomerNumber(customerRequest.getCustomerNumber());
                    customer.setFirstName(customerRequest.getFirstName());
                    customer.setLastName(customerRequest.getLastName());
                    customer.setAge(customerRequest.getAge());
                    customer.setOrders(customerRequest.getOrders());
                    return customerRepository.save(customer);
                }).orElseThrow(() -> new RecordNotFoundException("Customer with this number not found"));
    }

    // Delete customer by number
    public Customer deleteCustomerByNumber(Long customerNumber) {
        return customerRepository.findById(customerNumber).map(customer -> {
            customerRepository.delete(customer);
            return customer;
        }).orElseThrow(() -> new RecordNotFoundException("Customer with this number not found"));
    }
}
