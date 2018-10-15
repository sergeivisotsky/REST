package org.sergei.rest.service;

import org.sergei.rest.dao.CustomerDAO;
import org.sergei.rest.model.Customer;

import javax.inject.Inject;
import java.util.List;

public class CustomerService {

    @Inject
    private CustomerDAO customerDAO;

    public List<Customer> findAll() {
        return customerDAO.findAll();
    }

    public Customer findById(Long customerNumber) {
        return customerDAO.findOne(customerNumber);
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
