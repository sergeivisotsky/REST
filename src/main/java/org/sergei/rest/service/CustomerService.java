package org.sergei.rest.service;

import org.sergei.rest.exceptions.RecordNotFoundException;
import org.sergei.rest.model.Customer;
import org.sergei.rest.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerByNumber(Long customerNumber) {
        return customerRepository.findById(customerNumber)
                .orElseThrow(() -> new RecordNotFoundException("Customer with this number not found"));
    }

    // Save customer
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

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
        Customer customer = customerRepository.findByCustomerNumber(customerNumber);
        customer.setCustomerNumber(customerNumber);
        customerRepository.delete(customer);

        return customer;
    }
}
