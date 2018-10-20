package org.sergei.rest.service;

import org.sergei.rest.dao.CustomerDAO;
import org.sergei.rest.dto.CustomerDTO;
import org.sergei.rest.model.Customer;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerService {

    @Inject
    private CustomerDAO customerDAO;

    public List<CustomerDTO> findAll() {
        return customerDAO.findAll()
                .stream()
                .map(CustomerDTO::new)
                .collect(Collectors.toList());
    }

    public CustomerDTO findById(Long customerNumber) {
        Customer customer = customerDAO.findOne(customerNumber);
        return new CustomerDTO(customer);
    }

    public CustomerDTO save(CustomerDTO customerDTO) {
        Customer customer = customerDTO.toModelObject();
        customerDAO.save(customer);
        return customerDTO;
    }

    public CustomerDTO update(Long customerId, CustomerDTO customerDTO) {
        Customer customer = customerDTO.toModelObject();
        customer.setCustomerId(customerId);
        customerDAO.update(customer);
        return customerDTO;
    }

    public CustomerDTO delete(Long customerId) {
        Customer customer = customerDAO.findOne(customerId);
        customerDAO.delete(customer);
        return new CustomerDTO(customer);
    }
}
