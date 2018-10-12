package org.sergei.rest.service;

import org.modelmapper.ModelMapper;
import org.sergei.rest.dao.CustomerDAO;
import org.sergei.rest.dao.OrderDAO;
import org.sergei.rest.dao.OrderDetailsDAO;
import org.sergei.rest.dto.CustomerDTO;
import org.sergei.rest.dto.OrderDTO;
import org.sergei.rest.dto.OrderDetailsDTO;
import org.sergei.rest.model.Customer;
import org.sergei.rest.model.Order;
import org.sergei.rest.model.OrderDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class CustomerService {

    private final ModelMapper modelMapper;
    private final CustomerDAO customerDAO;
    private final OrderDAO orderDAO;
    private final OrderDetailsDAO orderDetailsDAO;

    @Autowired
    public CustomerService(ModelMapper modelMapper, CustomerDAO customerDAO,
                           OrderDAO orderDAO, OrderDetailsDAO orderDetailsDAO) {
        this.modelMapper = modelMapper;
        this.customerDAO = customerDAO;
        this.orderDAO = orderDAO;
        this.orderDetailsDAO = orderDetailsDAO;
    }

    /***
     * Get all customers
     * @return List of customer DTOs list
     */
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> customerDTOResponse = new LinkedList<>();

        List<Customer> customers = customerDAO.findAll();

        for (Customer customer : customers) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setCustomerNumber(customer.getCustomerNumber());
            customerDTO.setFirstName(customer.getFirstName());
            customerDTO.setLastName(customer.getLastName());
            customerDTO.setAge(customer.getAge());

            List<Order> orders = orderDAO.findAllByCustomerNumber(customer.getCustomerNumber());

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
        Customer customer = customerDAO.findOne(customerNumber);

        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setCustomerNumber(customer.getCustomerNumber());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setAge(customer.getAge());
        List<Order> orders = orderDAO.findAllByCustomerNumber(customerNumber);

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

        customerDAO.save(customer);

        return customerDTO;
    }

    //  TODO: Save customer and his orders with details in all request body

    /**
     * Update customer by customer number
     *
     * @param customerNumber         get customer number from the REST controller
     * @param customerDTORequestBody get customer as a request body
     * @return Return updated customer response
     */
    public CustomerDTO updateCustomer(Long customerNumber, CustomerDTO customerDTORequestBody) {
        Customer customer = modelMapper.map(customerDTORequestBody, Customer.class);

        customerDAO.update(customer);

        return customerDTORequestBody;
    }

    /**
     * Delete customer by number
     *
     * @param customerNumber get customer number from the REST controller
     * @return Return updated customer response
     * FIXME: Customer deletion doesn't work
     */
    public CustomerDTO deleteCustomerByNumber(Long customerNumber) {
        Customer customer = customerDAO.findOne(customerNumber);
//        CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setCustomerNumber(customer.getCustomerNumber());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setAge(customer.getAge());

//        List<Order> orders = orderRepository.findAllByCustomerNumber(customer.getCustomerNumber());

//        customer.setOrderDTOList(orders);

//        customerDTO.setOrderDTOList(setOrderDTOResponseFromEntityList(orders));

        customerDAO.delete(customer);
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
                    orderDetailsDAO.findAllByOrderNumber(orderDTO.getOrderNumber());

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
