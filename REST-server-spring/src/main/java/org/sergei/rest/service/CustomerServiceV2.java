/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.rest.service;

import org.sergei.rest.dto.CustomerExtendedDTO;
import org.sergei.rest.exceptions.ResourceNotFoundException;
import org.sergei.rest.model.Customer;
import org.sergei.rest.repository.CustomerRepository;
import org.sergei.rest.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Sergei Visotsky
 * @since 12/1/2018
 *
 * <pre>
 *     v1.1 for customer restful webservice service layer
 * </pre>
 */
@Service
public class CustomerServiceV2 {

    private static final String CUSTOMER_NOT_FOUND = "Customer with this ID not found";

    private final CustomerRepository customerRepository;
    private final PhotoRepository photoRepository;

    @Autowired
    public CustomerServiceV2(CustomerRepository customerRepository, PhotoRepository photoRepository) {
        this.customerRepository = customerRepository;
        this.photoRepository = photoRepository;
    }


    /***
     * V2 for for method to get all customers
     *
     * Get all customers
     * @return List of customer DTOs list
     */
    public List<CustomerExtendedDTO> findAllV2() {
        List<CustomerExtendedDTO> customerExtendedDTOList = new LinkedList<>();

        List<Customer> customers = customerRepository.findAll();

        for (Customer customer : customers) {
            CustomerExtendedDTO customerExtendedDTO = new CustomerExtendedDTO();
            List<String> fileUrls = photoRepository.findFileUrlByCustomerId(customer.getCustomerId());

            customerExtendedDTO.setCustomerId(customer.getCustomerId());
            customerExtendedDTO.setFirstName(customer.getFirstName());
            customerExtendedDTO.setLastName(customer.getLastName());
            customerExtendedDTO.setAge(customer.getAge());
            customerExtendedDTO.setPhotos(fileUrls);

            customerExtendedDTOList.add(customerExtendedDTO);
        }

        return customerExtendedDTOList;
    }

    /**
     * V2 for method to get customer by id
     *
     * @param customerId get customer number param from REST controller
     * @return Customer DTO response
     */
    public CustomerExtendedDTO findOneV1p1(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
                );
        List<String> fileUrls = photoRepository.findFileUrlByCustomerId(customerId);
        CustomerExtendedDTO customerExtendedDTO = new CustomerExtendedDTO();
        customerExtendedDTO.setCustomerId(customer.getCustomerId());
        customerExtendedDTO.setFirstName(customer.getFirstName());
        customerExtendedDTO.setLastName(customer.getLastName());
        customerExtendedDTO.setAge(customer.getAge());
        customerExtendedDTO.setPhotos(fileUrls);
        return customerExtendedDTO;
    }
}
