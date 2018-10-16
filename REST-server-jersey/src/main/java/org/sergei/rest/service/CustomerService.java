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
        return customerDAO.findAll().stream()
                .map(CustomerDTO::new)
                .collect(Collectors.toList());
    }

    public CustomerDTO findByCustomerNumber(Long customerNumber) {
        Customer customer = customerDAO.findOne(customerNumber);
        return new CustomerDTO(customer);
    }

    public void save(Customer entity) {
        customerDAO.save(entity);
    }

    public void update(Customer entity) {
        customerDAO.update(entity);
    }

    public void delete(Long customerNumber) {
        Customer customer = customerDAO.findOne(customerNumber);

        customerDAO.delete(customer);
    }
}
