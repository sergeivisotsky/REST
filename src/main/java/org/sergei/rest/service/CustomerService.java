package org.sergei.rest.service;

import org.sergei.rest.dao.CustomerDAO;
import org.sergei.rest.exceptions.RecordNotFoundException;
import org.sergei.rest.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerDAO.findAll();
    }

    public Customer getCustomerByNumber(Long customerNumber) {
        if (!customerDAO.existsByNumber(customerNumber)) {
            throw new RecordNotFoundException("No record with this parameters found");
        }

        return customerDAO.findByNumber(customerNumber);
    }

    // Save customer
    public void saveCustomer(Customer customer) {
        customerDAO.save(customer);
    }

    // Update customer by customer number
    public Customer updateCustomer(Long customerNumber, Customer customer) {
        customer.setCustomerNumber(customerNumber);
        if (!customerDAO.existsByNumber(customerNumber)) {
            throw new RecordNotFoundException("Record with this parameters not found");
        }
        customerDAO.updateRecord(customer, customerNumber);
        return customer;
    }

    // Delete customer by number
    public Customer deleteCustomerByNumber(Long customerNumber) {
        Customer customer = customerDAO.findByNumber(customerNumber);
        customer.setCustomerNumber(customerNumber);
        if (!customerDAO.existsByNumber(customerNumber)) {
            throw new RecordNotFoundException("Record with this parameters not found");
        }
        customerDAO.delete(customer);

        return customer;
    }
}
