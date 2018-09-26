package org.sergei.rest.service;

import org.sergei.rest.exceptions.RecordNotFoundException;
import org.sergei.rest.model.Customer;
import org.sergei.rest.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerByNumber(Long customerNumber) {
        if (!customerRepository.existsByCustomerNumber(customerNumber)) {
            throw new RecordNotFoundException("No record with this parameters found");
        }

        return customerRepository.findByCustomerNumber(customerNumber);
    }

    // Save customer
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    // Update customer by customer number
    public Customer updateCustomer(Long customerNumber, Customer customer) {
        customer.setCustomerNumber(customerNumber);
        if (!customerRepository.existsByCustomerNumber(customerNumber)) {
            throw new RecordNotFoundException("Record with this parameters not found");
        }
        customerRepository.save(customer);
        return customer;
    }

    // Delete customer by number
    public Customer deleteCustomerByNumber(Long customerNumber) {
        Customer customer = customerRepository.findByCustomerNumber(customerNumber);
        customer.setCustomerNumber(customerNumber);
        if (!customerRepository.existsByCustomerNumber(customerNumber)) {
            throw new RecordNotFoundException("Record with this parameters not found");
        }
        customerRepository.delete(customer);

        return customer;
    }
}
