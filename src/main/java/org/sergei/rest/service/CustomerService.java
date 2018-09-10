package org.sergei.rest.service;

import org.sergei.rest.dao.CustomerDAO;
import org.sergei.rest.exceptions.RecordNotFoundException;
import org.sergei.rest.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    public List<Customer> getAllCustomers() {
        return customerDAO.findAll();
    }

    public Customer getCustomerById(Long customerId) {
        if (!customerDAO.existsById(customerId)) {
            throw new RecordNotFoundException("No record with this parameters found");
        }

        return customerDAO.findById(customerId);
    }

    public void saveCustomer(Customer customer) {
        customerDAO.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        customer.setCustomerId(id);
        if (!customerDAO.existsById(id)) {
            throw new RecordNotFoundException("Record with this parameters not found");
        }
        customerDAO.updateRecord(customer, id);
        return customer;
    }

    public Customer deleteCustomerById(Long id) {
        Customer customer = customerDAO.findById(id);
        customer.setCustomerId(id);
        if (!customerDAO.existsById(id)) {
            throw new RecordNotFoundException("Record with this parameters not found");
        }
        customerDAO.delete(customer);

        return customer;
    }
}
