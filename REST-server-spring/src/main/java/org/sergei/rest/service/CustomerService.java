/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.rest.service;

import org.modelmapper.ModelMapper;
import org.sergei.rest.dao.CustomerDAO;
import org.sergei.rest.dao.OrderDAO;
import org.sergei.rest.dao.OrderDetailsDAO;
import org.sergei.rest.dto.CustomerDTO;
import org.sergei.rest.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
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
    public List<CustomerDTO> findAll() {
        List<CustomerDTO> customerDTOResponse = new LinkedList<>();

        List<Customer> customers = customerDAO.findAll();

        for (Customer customer : customers) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setCustomerId(customer.getCustomerId());
            customerDTO.setFirstName(customer.getFirstName());
            customerDTO.setLastName(customer.getLastName());
            customerDTO.setAge(customer.getAge());

            customerDTOResponse.add(customerDTO);
        }

        return customerDTOResponse;
    }

    /**
     * Get customer by number
     *
     * @param customerId get customer number param from REST controller
     * @return Customer DTO response
     */
    public CustomerDTO findOne(Long customerId) {
        Customer customer = customerDAO.findOne(customerId);
        return modelMapper.map(customer, CustomerDTO.class);
    }

    /**
     * Save customer
     *
     * @param customerDTO get customer from the REST controller as a request body
     */
    public CustomerDTO save(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customerDAO.save(customer);

        return customerDTO;
    }

    //  TODO: Save customer and his orders with details in a single request body

    /**
     * Update customer by customer number
     *
     * @param customerId  get customer number from the REST controller
     * @param customerDTO get customer as a request body
     * @return Return updated customer response
     */
    public CustomerDTO update(Long customerId, CustomerDTO customerDTO) {
        customerDTO.setCustomerId(customerId);

        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customerDAO.update(customer);

        return customerDTO;
    }

    /**
     * Delete customer by number
     *
     * @param aLong get customer number from the REST controller
     * @return Return updated customer response
     */
    public CustomerDTO deleteById(Long aLong) {
        Customer customer = customerDAO.findOne(aLong);
        customerDAO.delete(customer);
        return modelMapper.map(customer, CustomerDTO.class);
    }
}
