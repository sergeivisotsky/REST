package org.sergei.rest.service;

import org.modelmapper.ModelMapper;
import org.sergei.rest.dao.CustomerRepository;
import org.sergei.rest.dto.CustomerDTO;
import org.sergei.rest.exceptions.ResourceNotFoundException;
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

    private static final String CUSTOMER_NOT_FOUND = "Customer with this ID not found";

    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(ModelMapper modelMapper, CustomerRepository customerRepository) {
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
    }

    /***
     * Get all customers
     * @return List of customer DTOs list
     */
    public List<CustomerDTO> findAll() {
        List<CustomerDTO> customerDTOResponse = new LinkedList<>();

        List<Customer> customers = customerRepository.findAll();

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
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
                );
        return modelMapper.map(customer, CustomerDTO.class);
    }

    /**
     * Save customer
     *
     * @param customerDTO get customer from the REST controller as a request body
     */
    public CustomerDTO save(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customerRepository.save(customer);

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

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
                );
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setAge(customerDTO.getAge());

        customerRepository.save(customer);

        return customerDTO;
    }

    /**
     * Delete customer by number
     *
     * @param customerId get customer number from the REST controller
     * @return Updated customer response
     */
    public CustomerDTO deleteById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
                );
        customerRepository.delete(customer);
        return modelMapper.map(customer, CustomerDTO.class);
    }
}
