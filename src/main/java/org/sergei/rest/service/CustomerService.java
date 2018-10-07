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
    public CustomerService(ModelMapper modelMapper, CustomerRepository customerRepository,
                           OrderRepository orderRepository, OrderDetailsRepository orderDetailsRepository) {
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    /***
     * Get all customers
     * @return List of customer DTOs list
     */
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> customerDTOResponse = new LinkedList<>();

        List<Customer> customers = customerRepository.findAll();

        for (Customer customer : customers) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setCustomerNumber(customer.getCustomerNumber());
            customerDTO.setFirstName(customer.getFirstName());
            customerDTO.setLastName(customer.getLastName());
            customerDTO.setAge(customer.getAge());

            List<Order> orders = orderRepository.findAllByCustomerNumber(customer.getCustomerNumber());

            customerDTO.setOrderDTOList(setOrderDTOResponseFromEntityList(orders));
            customerDTOResponse.add(customerDTO);
        }

        return customerDTOResponse;
    }

    /**
     * Get customer by number
     *
     * @param customerNumber get customer number param from REST controller
     * @return Customer DTO response
     */
    public CustomerDTO getCustomerByNumber(Long customerNumber) {
        Customer customer = customerRepository.findById(customerNumber)
                .orElseThrow(() -> new RecordNotFoundException("Customer with this number not found"));
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setCustomerNumber(customer.getCustomerNumber());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setAge(customer.getAge());

        List<Order> orders = orderRepository.findAllByCustomerNumber(customerNumber);

        customerDTO.setOrderDTOList(setOrderDTOResponseFromEntityList(orders));

        return customerDTO;
    }

    /**
     * Save customer
     *
     * @param customerDTO get customer from the REST controller as a request body
     */
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();

        customer.setCustomerNumber(customerDTO.getCustomerNumber());
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setAge(customerDTO.getAge());

        customerRepository.save(customer);

        return customerDTO;
    }

    //  TODO: Save customer and his orders with details in all request body

    /**
     * Update customer by customer number
     *
     * @param customerNumber  get customer number from the REST controller
     * @param customerRequest get customer as a request body
     * @return Return updated customer response
     * TODO: Return customer DTO not customer entity
     */
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

    /**
     * Delete customer by number
     *
     * @param customerNumber get customer number from the REST controller
     * @return Return updated customer response
     * FIXME: Doesn't work
     */
    public CustomerDTO deleteCustomerByNumber(Long customerNumber) {
        Customer customer = customerRepository.findById(customerNumber)
                .orElseThrow(() -> new RecordNotFoundException("Customer with this number not found"));
//        CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setCustomerNumber(customer.getCustomerNumber());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setAge(customer.getAge());

        List<Order> orders = orderRepository.findAllByCustomerNumber(customer.getCustomerNumber());

//        customer.setOrderDTOList(orders);

        customerDTO.setOrderDTOList(setOrderDTOResponseFromEntityList(orders));

        customerRepository.delete(customer);
        return customerDTO;
    }

    /**
     * Util method to convert from the list of orders to the list order order DTOs
     *
     * @param orders get orders list to be replaced to OrderDTO response
     * @return List of order DTOs
     */
    private List<OrderDTO> setOrderDTOResponseFromEntityList(List<Order> orders) {
        List<OrderDTO> orderDTOList = new LinkedList<>();
        for (Order order : orders) {
            // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
            orderDTOList.add(orderDTO);

            // Get all order details list by order number
            List<OrderDetails> orderDetailsList =
                    orderDetailsRepository.findAllByOrderNumber(orderDTO.getOrderNumber());

            // Creating order details DTO and perform convertion from entity to DTO and put into
            List<OrderDetailsDTO> orderDetailsDTOS = new ArrayList<>();
            for (OrderDetails orderDetails : orderDetailsList) {
                // ModelMapper is used to avoid manual conversion from entity to DTO using setters and getters
                OrderDetailsDTO orderDetailsDTO = modelMapper.map(orderDetails, OrderDetailsDTO.class);
                orderDetailsDTOS.add(orderDetailsDTO);
            }

            // Set order detail DTO to the order DTO so that it was displayed as one response
            orderDTO.setOrderDetailsDTO(orderDetailsDTOS);
        }

        return orderDTOList;
    }
}
