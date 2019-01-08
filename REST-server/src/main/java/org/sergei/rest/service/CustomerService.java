package org.sergei.rest.service;

import org.sergei.rest.dto.CustomerDTO;
import org.sergei.rest.exceptions.ResourceNotFoundException;
import org.sergei.rest.model.Customer;
import org.sergei.rest.repository.CustomerRepository;
import org.sergei.rest.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public class CustomerService {

    protected final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /***
     * Get all customers
     * @return List of customer DTOs list
     */
    public List<CustomerDTO> findAll() {
        List<CustomerDTO> customerDTOList = new LinkedList<>();

        List<Customer> customers = customerRepository.findAll();

        customers.forEach(customer -> {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setCustomerId(customer.getCustomerId());
            customerDTO.setFirstName(customer.getFirstName());
            customerDTO.setLastName(customer.getLastName());
            customerDTO.setAge(customer.getAge());

            customerDTOList.add(customerDTO);
        });

        return customerDTOList;
    }

    /**
     * Get customer by id
     *
     * @param customerId get customer number param from REST controller
     * @return Customer DTO response
     */
    public CustomerDTO findOne(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.CUSTOMER_NOT_FOUND)
                );
        return ObjectMapperUtil.map(customer, CustomerDTO.class);
    }

    /**
     * Save customer
     *
     * @param customerDTO get customer from the REST controller as a request body
     */
    public CustomerDTO save(CustomerDTO customerDTO) {
        Customer customer = ObjectMapperUtil.map(customerDTO, Customer.class);
        Customer savedCustomer = customerRepository.save(customer);
        return ObjectMapperUtil.map(savedCustomer, CustomerDTO.class);
    }

    /**
     * Update customer by customer id
     *
     * @param customerId  get customer number from the REST controller
     * @param customerDTO get customer as a request body
     * @return Return updated customer response
     */
    public CustomerDTO update(Long customerId, CustomerDTO customerDTO) {
        customerDTO.setCustomerId(customerId);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.CUSTOMER_NOT_FOUND)
                );
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setAge(customerDTO.getAge());

        customerRepository.save(customer);

        return customerDTO;
    }

    /**
     * Delete customer by id
     *
     * @param customerId get customer number from the REST controller
     * @return Updated customer response
     */
    public CustomerDTO deleteById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.CUSTOMER_NOT_FOUND)
                );
        customerRepository.delete(customer);
        return ObjectMapperUtil.map(customer, CustomerDTO.class);
    }
}
